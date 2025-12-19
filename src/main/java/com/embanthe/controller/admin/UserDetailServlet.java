package com.embanthe.controller.admin;

import com.embanthe.dao.TransactionDAO;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.Transactions;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserDetailController", urlPatterns = {"/admin/user-detail"})
public class UserDetailServlet extends HttpServlet {


    private final UserDAO userDAO = new UserDAO();
    private final TransactionDAO transDAO = new TransactionDAO();

    public UserDetailServlet() throws SQLException {
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            int userId = Integer.parseInt(idStr);

            // 1. Lấy thông tin User
            Users user = userDAO.getUserById(userId);
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            int pageSize = 5;
            int totalRecords = transDAO.countTransactionsByUserId(userId);
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
            if (totalPages == 0) {
                totalPages = 1;
            }
            int page = 1;
            try {
                String pageStr = request.getParameter("page");
                if (pageStr != null) {
                    page = Integer.parseInt(pageStr);
                }
            } catch (NumberFormatException e) {
                page = 1;
            }

            if (page < 1) {
                page = 1;
            }

            if (page > totalPages) {
                page = totalPages;
            }

            List<Transactions> history = transDAO.getRecentTransactions(userId, page, pageSize);

            request.setAttribute("user", user);
            request.setAttribute("history", history);

            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/page/admin/UserDetail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/user-list");
        }
    }
}
