package com.embanthe.controller.account;

import javax.servlet.RequestDispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "NewPassword", urlPatterns = {"/newpassword"})


public class NewPasswordServlet extends HttpServlet {

    private final UserDAO userDAO;

    public NewPasswordServlet() {
        this.userDAO = new UserDAO(); // Khởi tạo một lần
    }
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
        String email = (String) session.getAttribute("email");
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
            request.setAttribute("message", "Password không hợp lệ. Yêu cầu 8-20 ký tự, có chữ hoa, số và ký tự đặc biệt");
            request.getRequestDispatcher("page/system/newPassword.jsp").forward(request, response);

        } else {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
            try {
                Users user = userDAO.getUserByEmail(email);
                if (user == null) {
                    request.setAttribute("message", "Không tìm thấy tài khoản với email này.");
                    dispatcher = request.getRequestDispatcher("page/system/login.jsp");
                } else {
                    // Dùng phương thức changePassword đã có trong UserDAO (theo userId)
                    boolean success = userDAO.changePassword(user.getUserId(), hashedPassword);

                    if (success) {
                        request.setAttribute("success", "Đặt lại mật khẩu thành công! Bạn có thể đăng nhập ngay.");
                    } else {
                        request.setAttribute("message", "Đặt lại mật khẩu thất bại. Vui lòng thử lại.");
                    }
                    dispatcher = request.getRequestDispatcher("page/system/login.jsp");
                }
                // Quan trọng: Xóa session sau khi hoàn tất (tránh reuse OTP/email)
                session.invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dispatcher.forward(request, response);
        }
    }
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        return password != null && password.matches(regex);
    }

}
