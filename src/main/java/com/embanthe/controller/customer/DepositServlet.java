package com.embanthe.controller.customer;

import com.embanthe.dao.TransactionDAO;
import com.embanthe.model.Transactions;
import com.embanthe.model.Users;
import com.embanthe.service.PaymentService;
import com.embanthe.util.VNPayUtils;
import com.embanthe.dao.UserDAO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DepositServlet", urlPatterns = {"/ewallet"})
public class DepositServlet extends HttpServlet {

    private final PaymentService paymentService = new PaymentService();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    private final UserDAO userDAO;

    private static String walletPath = "/page/customer/ewallet.jsp";

    public DepositServlet() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Users currentUser = (Users) session.getAttribute("user");

        if (currentUser != null) {
            Users updatedUser = userDAO.getUserById((int) currentUser.getUserId());
            if (updatedUser != null) {
                session.setAttribute("user", updatedUser);
                currentUser = updatedUser;
            }

            List<Transactions> transactionList = transactionDAO.getRecentTransactions((int) currentUser.getUserId(), 10);

            req.setAttribute("transactionList", transactionList);

        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.getRequestDispatcher(walletPath).forward(req, resp);
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
                request.getRequestDispatcher(walletPath).forward(request, response);
                return;
            }

            long amount = Long.parseLong(amountStr);

            if (amount < 10000 || amount > 50000000) {
                request.setAttribute("errorMessage", "Số tiền nạp phải từ 10.000đ đến 50.000.000đ");
                request.getRequestDispatcher(walletPath).forward(request, response);
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
                request.getRequestDispatcher(walletPath).forward(request, response);
                return;
            }
            String paymentUrl = paymentService.createDepositUrl((int) user.getUserId(), amount, ipAddr, String.valueOf(transactionId));

            response.sendRedirect(paymentUrl);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Số tiền không hợp lệ.");
            request.getRequestDispatcher(walletPath).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher(walletPath).forward(request, response);
        }
    }
}