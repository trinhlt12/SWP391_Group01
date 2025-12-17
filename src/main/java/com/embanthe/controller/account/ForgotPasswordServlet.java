package com.embanthe.controller.account;

import javax.servlet.RequestDispatcher;
import java.io.IOException;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(name = "ForgotPassword", urlPatterns = {"/forgotPassword"})
public class ForgotPasswordServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("page/system/forgotPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        HttpSession mySession = request.getSession();
        if (email != null && !email.trim().isEmpty()) {
            UserDAO userDAO = new UserDAO();
            Users user = userDAO.getUserByEmail(email);
            boolean isMailExist = userDAO.checkEmailExists(email);
            if (isMailExist && user != null) { // Sinh OTP
                int otpValue = 100000 + new Random().nextInt(900000);
                // Lưu OTP và email vào session trước
                mySession.setAttribute("otp", otpValue);
                mySession.setAttribute("email", email);
                mySession.setAttribute("actionType", "forgotPassword");
                mySession.setAttribute("otpAttempts", 0);
                // Chuyển ngay sang trang nhập OTP
                request.setAttribute("email", email);
                request.setAttribute("message", "OTP đã được gửi đến email của bạn!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("page/system/enterOTP.jsp");
                dispatcher.forward(request, response);
                // Gửi OTP bất đồng bộ
                new Thread(() -> {
                    try {
                        Properties props = new Properties();
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.socketFactory.port", "465");
                        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.port", "465");
                        Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication("hoangtvhe160642@fpt.edu.vn", "nxco yzoq vdrn upqv");
                            }
                        });
                        MimeMessage message = new MimeMessage(mailSession);
                        message.setFrom(new InternetAddress("hoangtvhe160642@fpt.edu.vn"));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
                        message.setSubject("Forgot Password");
                        message.setText("Your OTP is: " + otpValue + "\nThe OTP will expire in 30 minutes! Enter this OTP to reset password!");
                        Transport.send(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                request.setAttribute("message", "Thông tin email không tồn tại vui lòng thử lại!");
                request.getRequestDispatcher("page/system/forgotPassword.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "Vui lòng nhập email!");
            request.getRequestDispatcher("page/system/forgotPassword.jsp").forward(request, response);
        }
    }
}
