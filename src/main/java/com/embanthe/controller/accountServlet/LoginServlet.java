package com.embanthe.controller.accountServlet;

import com.embanthe.dao.AuthDAO;
import com.embanthe.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

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
                if (user.getRoleID() == 1) { //customer
                    response.sendRedirect("index.jsp");
                } else if (user.getRoleID() == 2) { //admin
                    response.sendRedirect(request.getContextPath() + "/admin");
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
