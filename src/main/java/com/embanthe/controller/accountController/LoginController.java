package com.embanthe.controller.accountController;

import com.embanthe.dao.AuthDAO;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        Cookie[] cookies = request.getCookies();
        String email = null;
        String password = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("email".equals(c.getName())) {
                    email = c.getValue();
                }
                if ("password".equals(c.getName())) {
                    password = c.getValue();
                }
            }
        }
        // Nếu không có code → Google từ chối hoặc lỗi
        if (code == null || code.isEmpty()) {
            request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
            return;
        }

        try {
            // B1: Lấy access token + thông tin từ Google
            String accessToken = GoogleLoginController.getToken(code);
            User googleUser  = GoogleLoginController.getUserInfo(accessToken);
            UserDAO userDAO = new UserDAO();
            User existingUser = userDAO.getUserByEmail(googleUser.getEmail());

            if (existingUser != null) {
                // Nếu email đã tồn tại trong DB → báo lỗi
                request.setAttribute("message", "Tài khoản với email này đã tồn tại. Vui lòng đăng nhập bằng email & mật khẩu.");
                request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
                return;
            }
            //chưa có → tạo mới
            googleUser.setRole("CUSTOMER");
            googleUser.setStatus("ACTIVE");
            googleUser.setBalance(0.0);
            userDAO.insertGoogleUser(googleUser);

            HttpSession session = request.getSession();
            session.setAttribute("user", googleUser);
            session.setAttribute("email", googleUser.getEmail());
            session.setAttribute("loginMethod", "google");
            response.sendRedirect(request.getContextPath() + "/googleConfirm");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mess", "Đăng nhập Google thất bại! Vui lòng thử lại.");
            request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        try {
            AuthDAO authDAO = new AuthDAO();
            User user = authDAO.login(email, password);

            if (user != null) {
                // Lưu user vào session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("email", user.getEmail());
                if (remember != null) {
                    Cookie cookieEmail = new Cookie("email", email);
                    Cookie cookiePass = new Cookie("password", password);
                    cookieEmail.setPath("/");
                    cookiePass.setPath("/");

                    cookieEmail.setMaxAge(2 * 60 * 60);
                    cookiePass.setMaxAge(2 * 60 * 60);

                    response.addCookie(cookieEmail);
                    response.addCookie(cookiePass);
                }
                // Kiểm tra role (CUSTOMER/ADMIN)
                if ("CUSTOMER".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                }  else {
                    // Nếu role không xác định, quay về trang login
                    request.setAttribute("message", "Role không hợp lệ!");
                    request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
                }

            } else {
                // Sai thông tin đăng nhập
                request.setAttribute("message", "Email hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
