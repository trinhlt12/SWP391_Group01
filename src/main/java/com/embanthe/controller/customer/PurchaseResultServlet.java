package com.embanthe.controller.customer;

import com.embanthe.dao.TransactionDAO;
import com.embanthe.model.Transactions;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/purchase-result")
public class PurchaseResultServlet extends HttpServlet {

    private final TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");

        // Check login
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String status = req.getParameter("status");
        req.setAttribute("status", status);

        if ("SUCCESS".equals(status)) {
            try {
                int orderId = Integer.parseInt(req.getParameter("orderId"));
                Transactions trans = transactionDAO.getTransactionByOrderId(orderId);

                if (trans != null && trans.getUserId() == user.getUserId()) {
                    req.setAttribute("trans", trans);
                } else {
                    req.setAttribute("status", "FAILED");
                    req.setAttribute("errorMessage", "Không tìm thấy thông tin giao dịch.");
                }
            } catch (Exception e) {
                req.setAttribute("status", "FAILED");
                req.setAttribute("errorMessage", "Dữ liệu không hợp lệ.");
            }

        } else {
            String rawMsg = req.getParameter("message");
            String errorMsg = "Giao dịch thất bại";
            if (rawMsg != null) {
                // Giải mã tiếng Việt từ URL
                errorMsg = URLDecoder.decode(rawMsg, StandardCharsets.UTF_8.toString());
            }
            req.setAttribute("errorMessage", errorMsg);
        }

        req.getRequestDispatcher("/page/customer/purchase-result.jsp").forward(req, resp);
    }
}