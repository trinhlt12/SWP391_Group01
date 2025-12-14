package com.embanthe.controller.adminController;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.User;
import com.embanthe.util.PasswordUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CreateNewUserController", urlPatterns = {"/admin/user-create"})
public class CreateNewUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/admin/user-list");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();

        try {
            String username = request.getParameter("username");
            String passwordRaw = request.getParameter("password");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");
            String status = request.getParameter("status");

            // 2. Validate cơ bản (Kiểm tra trùng username/email)
            if (userDAO.checkUsernameExists(username)) {
                session.setAttribute("error", "Thất bại: Tên đăng nhập '" + username + "' đã tồn tại!");
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            if (userDAO.checkEmailExists(email)) {
                session.setAttribute("error", "Thất bại: Email '" + email + "' đã được sử dụng!");
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            // 3. Mã hóa mật khẩu
            String passwordHash = PasswordUtil.hash(passwordRaw);

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPasswordHash(passwordHash);
            newUser.setFullName(fullname);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setRole(role);
            newUser.setStatus(status);

            // 5. Gọi DAO để insert
            boolean isSuccess = userDAO.insertUser(newUser);

            if (isSuccess) {
                session.setAttribute("message", "Thêm mới thành công user: " + username);
            } else {
                session.setAttribute("error", "Lỗi database: Không thể thêm mới.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
        }

        // 6. Quay về trang danh sách
        response.sendRedirect(request.getContextPath() + "/admin/user-list");
    }
}
