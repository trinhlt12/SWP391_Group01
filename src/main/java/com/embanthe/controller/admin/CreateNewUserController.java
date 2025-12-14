package com.embanthe.controller.admin;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;
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


        try {
            UserDAO userDAO = new UserDAO();
            String username = request.getParameter("username");
            String passwordRaw = request.getParameter("password");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");
            String status = request.getParameter("status");

            // 1. Validate backend
            String error = validateUserCreate(
                    username,
                    passwordRaw,
                    fullname,
                    email,
                    phone,
                    role,
                    status,
                    userDAO
            );

            if (error != null) {
                session.setAttribute("error", error);
                session.setAttribute("showCreateModal", true);
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            // 2. Hash password
            String passwordHash = PasswordUtil.hash(passwordRaw);

            // 3. Build User object
            Users newUser = new Users();
            newUser.setUsername(username);
            newUser.setPasswordHash(passwordHash);
            newUser.setFullName(fullname);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setRole(role);
            newUser.setStatus(status);

            // 4. Insert DB
            boolean isSuccess = userDAO.insertUser(newUser);

            if (isSuccess) {
                session.setAttribute("message", "Thêm mới thành công user: " + username);
            } else {
                session.setAttribute("error", "Lỗi database: Không thể thêm mới user.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/user-list");
    }

    /**
     * Validate backend khi tạo user mới
     */
    private String validateUserCreate(
            String username,
            String password,
            String fullname,
            String email,
            String phone,
            String role,
            String status,
            UserDAO userDAO
    ) {

        // 1. Required fields
        if (username == null || username.trim().isEmpty())
            return "Username không được để trống";

        if (password == null || password.length() < 6)
            return "Mật khẩu phải có ít nhất 6 ký tự";

        if (fullname == null || fullname.trim().isEmpty())
            return "Họ tên không được để trống";

        if (email == null || email.trim().isEmpty())
            return "Email không được để trống";

        // 2. Format validation
        if (!username.matches("^[a-zA-Z0-9_]{4,30}$"))
            return "Username phải 4–30 ký tự, không dấu, không khoảng trắng";

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            return "Email không đúng định dạng";

        if (phone != null && !phone.isBlank() && !phone.matches("^\\d{10}$"))
            return "Số điện thoại phải gồm đúng 10 chữ số";

        // 3. Business rules
        if (!("ADMIN".equals(role) || "CUSTOMER".equals(role)))
            return "Vai trò không hợp lệ";

        if (!("ACTIVE".equals(status) || "INACTIVE".equals(status)))
            return "Trạng thái không hợp lệ";

        if (userDAO.checkUsernameExists(username))
            return "Tên đăng nhập đã tồn tại";

        if (userDAO.checkEmailExists(email))
            return "Email đã được sử dụng";

        return null; // hợp lệ
    }
}
