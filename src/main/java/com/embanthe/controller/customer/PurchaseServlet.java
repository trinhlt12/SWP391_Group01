package com.embanthe.controller.customer;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;
import com.embanthe.service.PurchaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {

    private final PurchaseService purchaseService = new PurchaseService();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            String message = "Vui lòng đăng nhập để mua hàng";
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());

            resp.sendRedirect(req.getContextPath() + "/login?message=" + encodedMessage);
            return;
        }

        try {
            String productIdStr = req.getParameter("productId");
            String quantityStr = req.getParameter("quantity");

            if (productIdStr == null || quantityStr == null || productIdStr.isEmpty() || quantityStr.isEmpty()) {
                forwardWithError(req, resp, "Dữ liệu không hợp lệ.");
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            if (quantity <= 0 || quantity > 100) {
                forwardWithError(req, resp, "Số lượng phải từ 1 đến 100.");
                return;
            }

            String result = purchaseService.processPurchase(user.getUserId(), productId, quantity);
            System.out.println("DEBUG: Purchase Result = " + result);


            if (result.startsWith("SUCCESS")) {

                String orderId = result.split("\\|")[1];
                System.out.println("DEBUG: Order ID = " + orderId);

                Users updatedUser = userDAO.getUserById(user.getUserId());
                session.setAttribute("user", updatedUser);

                resp.sendRedirect(req.getContextPath() + "/purchase-result?status=SUCCESS&orderId=" + orderId);
            } else {
                String encodedMsg = URLEncoder.encode(result, StandardCharsets.UTF_8.toString());
                resp.sendRedirect(req.getContextPath() + "/purchase-result?status=FAILED&message=" + encodedMsg);

            }

        } catch (NumberFormatException e) {
            forwardWithError(req, resp, "Lỗi định dạng dữ liệu.");
        } catch (Exception e) {
            e.printStackTrace();
            forwardWithError(req, resp, "Đã xảy ra lỗi hệ thống.");
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
            throws ServletException, IOException {

        req.setAttribute("errorMessage", errorMessage);

        req.getRequestDispatcher("/service").forward(req, resp);
    }
}