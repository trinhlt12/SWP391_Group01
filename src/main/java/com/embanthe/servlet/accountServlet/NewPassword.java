package com.embanthe.servlet.accountServlet;

import jakarta.servlet.RequestDispatcher;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "NewPassword", urlPatterns = {"/newpassword"})


public class NewPassword extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String newPassword = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");

        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        RequestDispatcher dispatcher = null;
        if (newPassword == null || confPassword == null) {
            request.setAttribute("message", "Password hoặc Confirm Password không được để trống");
            request.getRequestDispatcher("page/system/newPassword.jsp").forward(request, response);

        } else if (!newPassword.equals(confPassword)) {
            request.setAttribute("message", "Password và Confirm Password không khớp");
            request.getRequestDispatcher("page/system/newPassword.jsp").forward(request, response);

        } else if (!isValidPassword(newPassword)) {
            request.setAttribute("message", "Password không hợp lệ. Yêu cầu ít nhất 8 ký tự, có chữ hoa, số và ký tự đặc biệt");
            request.getRequestDispatcher("page/system/newPassword.jsp").forward(request, response);

        } else {
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/swp391?useSSL=false&serverTimezone=UTC";

                String username = "root";
                String password = "Macuongthi444.";
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);

                String sql = "UPDATE Users SET password_hash = ? WHERE email = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, hashed);
                pst.setString(2, (String) session.getAttribute("email"));
                int rowCount = pst.executeUpdate();
                if (rowCount ==1) {
                    request.setAttribute("message", "Reset Success");

                    request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "Reset Failed");


                    request.getRequestDispatcher("page/system/login.jsp").forward(request, response);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(regex);
    }

}
