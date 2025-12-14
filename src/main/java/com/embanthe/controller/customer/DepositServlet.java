package com.embanthe.controller.customer;

import com.embanthe.dao.TransactionDAO;
import com.embanthe.model.Transactions;
import com.embanthe.model.Users;
import com.embanthe.service.PaymentService;
import com.embanthe.util.VNPayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DepositServlet", urlPatterns = {"/deposit"})
public class DepositServlet extends HttpServlet {

    private final PaymentService paymentService = new PaymentService();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("page/user/deposit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?message=Please login first");
            return;
        }

        try {
            String amountStr = request.getParameter("amount");
            if (amountStr == null || amountStr.isEmpty()) {
                request.setAttribute("errorMessage", "Vui lòng nhập số tiền.");
                request.getRequestDispatcher("src/main/webapp/page/customer/ewallet.jsp").forward(request, response);
                return;
            }

            long amount = Long.parseLong(amountStr);

            if (amount < 10000 || amount > 50000000) {
                request.setAttribute("errorMessage", "Số tiền nạp phải từ 10.000đ đến 50.000.000đ");
                request.getRequestDispatcher("src/main/webapp/page/customer/ewallet.jsp").forward(request, response);
                return;
            }

            String ipAddr = VNPayUtils.getIpAddress(request);

            Transactions trans = new Transactions();
            trans.setUserId((int) user.getUserId());
            trans.setAmount((double) amount);
            trans.setType("DEPOSIT");
            trans.setStatus("PENDING");
            trans.setMessage("Nạp tiền vào ví qua VNPay");
            trans.setOrderId(0);

            int transactionId = transactionDAO.createDepositTransaction(trans);

            if (transactionId == -1) {
                request.setAttribute("errorMessage", "Lỗi hệ thống: Không thể tạo giao dịch.");
                request.getRequestDispatcher("page/user/deposit.jsp").forward(request, response);
                return;
            }
            String paymentUrl = paymentService.createDepositUrl((int) user.getUserId(), amount, ipAddr, String.valueOf(transactionId));

            response.sendRedirect(paymentUrl);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Số tiền không hợp lệ.");
            request.getRequestDispatcher("page/user/deposit.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("page/user/deposit.jsp").forward(request, response);
        }
    }
}