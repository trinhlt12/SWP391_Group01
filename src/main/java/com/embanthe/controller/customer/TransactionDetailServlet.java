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

@WebServlet(name = "TransactionDetailServlet", urlPatterns = {"/transaction-detail"})
public class TransactionDetailServlet extends HttpServlet {

    private final TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/ewallet");
            return;
        }

        try {
            int transId = Integer.parseInt(idStr);

            Transactions trans = transactionDAO.getTransactionById(transId);

            if (trans == null || trans.getUserId() != user.getUserId()) {
                req.setAttribute("errorMessage", "Giao dịch không tồn tại hoặc bạn không có quyền truy cập.");
                req.getRequestDispatcher("/page/customer/ewallet.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("trans", trans);
            req.getRequestDispatcher("/page/customer/transaction-detail.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/ewallet");
        }
    }
}