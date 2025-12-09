package com.embanthe.Servlet;

import com.embanthe.DAO.AuthDAO;
import com.embanthe.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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
                session.setAttribute("currentUser", user);

                // Chuyển hướng sang trang welcome
                response.sendRedirect("welcome.jsp");
            } else {
                // Sai thông tin đăng nhập
                request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
