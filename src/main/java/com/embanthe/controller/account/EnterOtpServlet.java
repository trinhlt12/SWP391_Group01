package com.embanthe.controller.account;

import com.embanthe.dao.AuthDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(name = "ValidateOtp", urlPatterns = {"/validateOtp"})
public class EnterOtpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int enteredOtp = Integer.parseInt(request.getParameter("otp"));
        HttpSession session = request.getSession();
        int otp = (int) session.getAttribute("otp");
        String actionType = (String) session.getAttribute("actionType");
        // Lấy số lần nhập sai OTP từ session
        Long otpGeneratedTime = (Long) session.getAttribute("otpGeneratedTime");
        if (otpGeneratedTime == null || System.currentTimeMillis() - otpGeneratedTime > 30 * 60 * 1000) {
            session.invalidate();
            request.setAttribute("message", "OTP đã hết hạn. Vui lòng đăng ký lại!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("page/system/register.jsp");
            dispatcher.forward(request, response);
            return;
        }
        Integer otpAttempts = (Integer) session.getAttribute("otpAttempts");
        if (otpAttempts == null) {
            otpAttempts = 0;
        }
        RequestDispatcher dispatcher;
        if (enteredOtp == otp) { // Reset số lần nhập khi đúng
            session.setAttribute("otpAttempts", 0);
            if ("forgotPassword".equals(actionType)) {
                request.setAttribute("email", session.getAttribute("email"));
                request.setAttribute("status", "success");
                dispatcher = request.getRequestDispatcher("page/system/newPassword.jsp");
                dispatcher.forward(request, response);
            } else if ("register".equals(actionType)) {
                String username = (String) session.getAttribute("username");
                String fullName = (String) session.getAttribute("fullName");
                String email = (String) session.getAttribute("email");
                String password = (String) session.getAttribute("password");
                String phone = (String) session.getAttribute("phone");
                try {
                    AuthDAO authDAO = new AuthDAO();
                    boolean success = authDAO.register(username, fullName, email, password, phone);
                    if (success) {
                        request.setAttribute("success", "Đăng ký thành công! Bạn có thể đăng nhập.");
                        dispatcher = request.getRequestDispatcher("page/system/login.jsp");
                    } else {
                        request.setAttribute("message", "Email đã tồn tại!");
                        dispatcher = request.getRequestDispatcher("page/system/register.jsp");
                    }
                    dispatcher.forward(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("message", "Lỗi hệ thống!");
                    request.getRequestDispatcher("page/system/register.jsp").forward(request, response);
                }
            }
        } else {
            otpAttempts++;
            session.setAttribute("otpAttempts", otpAttempts);
            if (otpAttempts >= 5) {
                session.invalidate(); // Xóa session để tránh lưu thông tin cũ
                request.setAttribute("message", "Bạn đã nhập sai quá 5 lần. Vui lòng nhập lại!");
                dispatcher = request.getRequestDispatcher("page/system/login.jsp");
                dispatcher.forward(request, response);
                return;
            } else {
                request.setAttribute("message", "Sai OTP! Bạn còn " + (5 - otpAttempts) + " lần thử.");
                request.setAttribute("email", session.getAttribute("email"));
                dispatcher = request.getRequestDispatcher("page/system/enterOTP.jsp");
                dispatcher.forward(request, response);
            }

        }
    }
}
