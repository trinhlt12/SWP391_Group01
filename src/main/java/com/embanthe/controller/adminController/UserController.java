package com.embanthe.controller.adminController;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserListServlet", urlPatterns = {"/admin/user-list"})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            String keyword = request.getParameter("keyword");
            String role = request.getParameter("role");
            String username = request.getParameter("username");
            String status = request.getParameter("status");
            // 1. Gọi DAO để lấy danh sách từ Database
            UserDAO userDAO = new UserDAO();
            List<User> userList;

            if ((keyword != null && !keyword.trim().isEmpty()) ||
                    (role != null && !role.trim().isEmpty()) ||
                    (status != null && !status.trim().isEmpty())) {

                userList = userDAO.searchUsers(keyword, role, status,null, null, null);

            } else {
                userList = userDAO.getAll();
            }

            request.setAttribute("listUser", userList);
            request.getRequestDispatcher("/page/admin/ManagerUser.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu lỗi thì hiển thị thông báo đơn giản (hoặc chuyển sang trang error.jsp)
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy danh sách User: " + e.getMessage());
        }
    }
}
