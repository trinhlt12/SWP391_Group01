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

        // Nếu không có code → Google từ chối hoặc lỗi
        if (code == null || code.isEmpty()) {
            request.setAttribute("message", "Đăng nhập Google bị hủy hoặc lỗi!");
            request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
            return;
        }

        try {
            // B1: Lấy access token + thông tin từ Google
            String accessToken = GoogleLoginController.getToken(code);
            User user = GoogleLoginController.getUserInfo(accessToken);  // hàm bạn đã sửa ở tin trước

            // B2: Dùng chung AuthDAO để kiểm tra hoặc tạo user (nếu chưa có thì đã tự tạo trong getUserInfo)

            UserDAO userDAO = new UserDAO();
            User existingUser = userDAO.getUserByEmail(user.getEmail());

            if (existingUser != null) {
                user = existingUser; // dùng user trong DB (đảm bảo đầy đủ userId, balance, v.v.)
            }

            // B3: Đăng nhập thành công → lưu session giống hệt login thường
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("email", user.getEmail());
            session.setAttribute("loginMethod", "google");

            // B4: Chuyển hướng theo role (giống hệt LoginController)
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Đăng nhập Google thất bại! Vui lòng thử lại.");
            request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            AuthDAO authDAO = new AuthDAO();
            User user = authDAO.login(email, password);

            if (user != null) {
                // Lưu user vào session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("email", user.getEmail());

                // Kiểm tra role (CUSTOMER/ADMIN)
                if ("CUSTOMER".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                } else {
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
