package com.embanthe.service;

import com.embanthe.dao.*;
import com.embanthe.model.CardItems;
import com.embanthe.model.Products;
import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PurchaseService {
    private final ProductDAO productDAO = new ProductDAO();
    private final CardItemDAO cardItemDAO = new CardItemDAO();
    private final UserDAO userDAO = new UserDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public String PurchaseService(int userId, int productId, int quantity) {
        Connection conn = null;
        try {
            conn = DBContext.getInstance().getConnection();
            conn.setAutoCommit(false);

            //validation:
            Products product = productDAO.getProductById(productId);
            if (product == null) {
                return "Sản phẩm không tồn tại hoặc đã bị xóa";
            }

            double totalAmount = product.getPrice() * quantity;

            //locking:
            double currentBalance = userDAO.getBalanceForUpdate(conn, userId);

            if (currentBalance < totalAmount) {
                conn.rollback();
                return "Số dư trong ví không đủ để thực hiện giao dịch này";
            }

            List<CardItems> cardsToSell = cardItemDAO.getAvailableCardsForUpdate(conn, productId, quantity);

            if (cardsToSell.size() < quantity) {
                conn.rollback();
                return "Kho thẻ hiện tại không đủ số lượng chỉ định (Chỉ còn " + cardsToSell.size() + ")";
            }

            //execution:
            double newBalance = currentBalance - totalAmount;
            userDAO.updateBalance(conn, userId, newBalance);

            int orderId = orderDAO.createOrder(conn, userId, productId, quantity, totalAmount);
            for (CardItems card : cardsToSell) {
                cardItemDAO.updateCardStatusToSold(conn, card.getCardItemId(), orderId);
            }

            boolean updateQuantitySucess = productDAO.adjustQuantityWithCheck(conn, productId, -quantity);
            if (!updateQuantitySucess) {
                conn.rollback();
                return "Lỗi cập nhật kho hàng";
            }

            String transactionMessage = "Mua" + quantity + " mã thẻ" + product.getProductName();
            transactionDAO.createTransaction(conn, userId, orderId, totalAmount, transactionMessage);
            conn.commit();
            return "SUCCESS!" + orderId;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return "Lỗi hệ thống: " + e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
