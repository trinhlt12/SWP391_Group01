package com.embanthe.controller.userProfile;

import com.embanthe.dao.AuthDAO;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/userprofile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy email từ session (đã lưu khi login)
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            // Nếu chưa đăng nhập thì chuyển về trang login
            response.sendRedirect("login");
            return;
        }
        UserDAO userDAO = new UserDAO();
        Users user = userDAO.getUserByEmail(email);
        if (user == null) {
            request.getRequestDispatcher("home").forward(request, response);
            return;
        }
        // Đưa user vào request attribute để hiển thị ở JSP
        request.setAttribute("user", user);
        // Forward sang trang JSP để render thông tin
        request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy email từ session
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            response.sendRedirect("login");
            return;
        }

        // Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String newEmail = request.getParameter("email");
        String phone = request.getParameter("phone");

        if (username == null || username.trim().isEmpty()
                || fullName == null || fullName.trim().isEmpty()
                || newEmail == null || newEmail.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()) {
            request.setAttribute("error", "Các trường không được để trống!");
            request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            AuthDAO authDAO = new AuthDAO();
            Users user = userDAO.getUserByEmail(email);
            if (user == null) {
                response.sendRedirect("home");
                return;
            }    if (!username.equals(user.getUsername()) && authDAO.isUsernameExists(username)) {
                request.setAttribute("error", "Username đã tồn tại!");
                request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
                return;
            }

            // Kiểm tra trùng phone
            if (!phone.equals(user.getPhone()) && authDAO.isPhoneExists(phone)) {
                request.setAttribute("error", "Số điện thoại đã tồn tại!");
                request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
                return;
            }
            // Cập nhật thông tin
            user.setUsername(username);
            user.setFullName(fullName);
            user.setEmail(newEmail);
            user.setPhone(phone);
            boolean updated = userDAO.updateUser(user);
            if (updated) {
                // Nếu email thay đổi thì cập nhật lại session
                request.getSession().setAttribute("email", user.getEmail());
                request.getSession().setAttribute("user", user);
                // Chuyển hướng lại trang profile
                response.sendRedirect("userprofile");
            } else {
                request.setAttribute("error", "Không thể cập nhật thông tin!");
                request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
