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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("page/system/forgotPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        RequestDispatcher dispatcher = null;
        int otpvalue = 0;
        HttpSession mySession = request.getSession();

        if (email != null && !email.equals("")) {
            UserDAO userDAO = new UserDAO();
            Users user = userDAO.getUserByEmail(email);
            boolean isMailExist = userDAO.checkEmailExists(email);
            if (isMailExist) {
                // sending otp
                Random rand = new Random();
                otpvalue = 100000 + rand.nextInt(900000);

                String to = user.getEmail();

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");
                Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("hoangtvhe160642@fpt.edu.vn", "nxco yzoq vdrn upqv");// Put your email
                    }
                });
                // compose message
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(email));// change accordingly
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject("Forgot Password");
                    message.setText("Your OTP is: " + otpvalue + "\nThe OTP will expire in 30 minutes! Enter this OTP to reset password!\nRegrad!");
                    // send message
                    Transport.send(message);

                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                request.setAttribute("email", email);
                dispatcher = request.getRequestDispatcher("page/system/enterOTP.jsp");
                request.setAttribute("message", "OTP is sent to your email");
                //request.setAttribute("connection", con);
                mySession.setAttribute("otp", otpvalue);
                mySession.setAttribute("email", email);
                dispatcher.forward(request, response);
                //request.setAttribute("status", "success");
            } else {
                request.setAttribute("message", "Thông tin email không tồn tại vui lòng thử lại!");
                request.getRequestDispatcher("page/system/forgotPassword.jsp").forward(request, response);
            }
        }

    }

}
