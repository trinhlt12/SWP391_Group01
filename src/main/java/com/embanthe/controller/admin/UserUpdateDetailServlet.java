package com.embanthe.controller.admin;

import com.embanthe.dao.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserUpdateDetailController", urlPatterns = {"/admin/user-update"})
public class UserUpdateDetailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String idStr = request.getParameter("id");

        try {
            // 1. Lấy dữ liệu
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");
            String status = request.getParameter("status");

            int userId = Integer.parseInt(idStr);

            // 2. Validate cơ bản
            if (fullName == null || email == null || fullName.trim().isEmpty()) {
                session.setAttribute("error", "Tên và Email không được để trống!");
                // Redirect lại trang chi tiết đúng ID đó
                response.sendRedirect(request.getContextPath() + "/admin/user-detail?id=" + userId);
                return;
            }

            // 3. Gọi DAO update thông tin
            UserDAO userDAO = new UserDAO();
            boolean isUpdated = userDAO.updateUserInfo(userId, fullName, email, phone, role, status);

            if (isUpdated) {
                session.setAttribute("message", "Cập nhật thông tin thành công!");
            } else {
                session.setAttribute("error", "Lỗi cập nhật! Có thể Email đã tồn tại.");
            }

            // Redirect lại trang chi tiết
            response.sendRedirect(request.getContextPath() + "/admin/user-detail?id=" + userId);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống.");
            response.sendRedirect(request.getContextPath() + "/admin/user-list");
        }
    }
}