package com.embanthe.servlet.accountServlet;

import com.embanthe.dao.AuthDAO;
import com.embanthe.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
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
