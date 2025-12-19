package com.embanthe.controller.admin;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;

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

            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");
            String status = request.getParameter("status");

            // Validate ID
            if (idStr == null || idStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }
            int userId = Integer.parseInt(idStr);


            if (fullName == null || email == null || fullName.trim().isEmpty()) {
                session.setAttribute("error", "Tên và Email không được để trống!");

                response.sendRedirect(request.getContextPath() + "/admin/user-detail?id=" + userId);
                return;
            }

            // 3. Tạo đối tượng Users để truyền vào DAO (SỬA Ở ĐÂY)
            Users userToUpdate = Users.builder()
                    .userId(userId)
                    .fullName(fullName)
                    .email(email)
                    .phone(phone)
                    .role(role)
                    .status(status)
                    .build();

            // 4. Gọi DAO update
            UserDAO userDAO = new UserDAO();

            // Hàm updateUser trong DAO nhận vào đối tượng Users
            boolean isUpdated = userDAO.updateUser(userToUpdate);

            if (isUpdated) {
                session.setAttribute("message", "Cập nhật thông tin thành công!");
            } else {
                session.setAttribute("error", "Lỗi cập nhật! Có thể Email đã tồn tại hoặc lỗi server.");
            }

            response.sendRedirect(request.getContextPath() + "/admin/user-detail?id=" + userId);

        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID người dùng không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/admin/user-list");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/user-list");
        }
    }
}