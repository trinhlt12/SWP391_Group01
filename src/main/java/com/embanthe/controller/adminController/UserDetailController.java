package com.embanthe.controller.adminController;

import com.embanthe.dao.TransactionsDAO;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.Transactions;
import com.embanthe.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserDetailController", urlPatterns = {"/admin/user-detail"})
public class UserDetailController extends HttpServlet {

    // Khởi tạo DAO trực tiếp (Vì DAO của bạn giờ đã an toàn, không ném Exception khi new)
    private final UserDAO userDAO = new UserDAO();
    private final TransactionsDAO transDAO = new TransactionsDAO();

    // --- KHÔNG CẦN VIẾT CONSTRUCTOR ---
    // Java sẽ tự tạo constructor mặc định rỗng

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String idStr = request.getParameter("id");

            if (idStr == null || idStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            int userId = Integer.parseInt(idStr);

            // 2. Lấy thông tin User
            User user = userDAO.getUserById(userId);

            if (user == null) {

                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            List<Transactions> history = transDAO.getRecentTransactions(userId);

            // 4. Đẩy dữ liệu sang JSP
            request.setAttribute("user", user);
            request.setAttribute("history", history);

            // Chuyển hướng sang trang giao diện
            request.getRequestDispatcher("/page/admin/UserDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // ID trên URL không phải số
            response.sendRedirect(request.getContextPath() + "/admin/user-list");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp"); // Trang lỗi nếu có
        }
    }
}
