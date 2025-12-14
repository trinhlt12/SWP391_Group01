package com.embanthe.controller.customer;

import com.embanthe.dao.TransactionDAO;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.Transactions;
import com.embanthe.service.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "PaymentReturnServlet", urlPatterns = {"/payment-return"})
public class PaymentReturnServlet extends HttpServlet {

    private final PaymentService paymentService = new PaymentService();
    private final TransactionDAO transactionDAO = new TransactionDAO();
    // UserDAO của bạn ném SQLException ở constructor nên cần try-catch hoặc init
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAO();
        } catch (SQLException e) {
            throw new ServletException("Cannot init UserDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_Amount = request.getParameter("vnp_Amount");

        double amount = Double.parseDouble(vnp_Amount) / 100;
        int transactionId = Integer.parseInt(vnp_TxnRef);

        int checkStatus = paymentService.verifyPaymentUrl(request.getParameterMap());

        String message = "";
        String statusAlert = ""; // success | danger | warning

        if (checkStatus == 1) {

            Transactions trans = transactionDAO.getTransactionById(transactionId);

            if (trans != null && "PENDING".equals(trans.getStatus())) {
                boolean updateBalanceSuccess = userDAO.updateBalance((int) trans.getUserId(), amount);

                if (updateBalanceSuccess) {
                    transactionDAO.updateTransactionStatus(transactionId, "SUCCESS", "Giao dịch thành công");
                    message = "Nạp tiền thành công! Số dư của bạn đã được cập nhật.";
                    statusAlert = "success";
                } else {
                    transactionDAO.updateTransactionStatus(transactionId, "FAILED", "Lỗi cộng tiền vào ví");
                    message = "Thanh toán thành công nhưng lỗi cộng tiền. Vui lòng liên hệ Admin.";
                    statusAlert = "warning";
                }
            } else {
                message = "Giao dịch đã được ghi nhận trước đó.";
                statusAlert = "info";
            }

        } else if (checkStatus == 0) {
            transactionDAO.updateTransactionStatus(transactionId, "FAILED", "Thanh toán thất bại/Hủy. Mã lỗi: " + vnp_ResponseCode);
            message = "Giao dịch thất bại hoặc bạn đã hủy thanh toán.";
            statusAlert = "danger";

        } else {
            message = "Cảnh báo: Dữ liệu trả về không hợp lệ (Sai chữ ký)!";
            statusAlert = "danger";
        }

        request.setAttribute("message", message);
        request.setAttribute("statusAlert", statusAlert);
        request.setAttribute("amount", amount);

        request.getRequestDispatcher("page/user/payment_result.jsp").forward(request, response);
    }
}