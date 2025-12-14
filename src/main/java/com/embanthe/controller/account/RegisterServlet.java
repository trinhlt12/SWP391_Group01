package com.embanthe.controller.account;

import com.embanthe.dao.AuthDAO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private final AuthDAO authDAO;


    public RegisterServlet() {
        try {
            this.authDAO = new AuthDAO();

        } catch (SQLException e) {
            throw new RuntimeException("Không thể kết nối database khi khởi tạo RegisterServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");


        if (isEmpty(username) || isEmpty(fullName) || isEmpty(email) || isEmpty(phone)
                || isEmpty(password) || isEmpty(confirmPassword)) {
            request.setAttribute("message", "Vui lòng điền đầy đủ thông tin!");
            request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
            return;
        }
        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
            return;
        }
        if (!isValidPassword(password)) {
            request.setAttribute("message", "Mật khẩu phải có ít nhất 1 chữ in hoa, 1 số và 1 ký tự đặc biệt, tối thiểu 8 ký tự!");
            request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
            return;
        }

        try {
            boolean usernameExist = authDAO.isUsernameExists(username);
            boolean phoneExist = authDAO.isPhoneExists(phone);
            boolean emailExist = authDAO.isEmailExists(email);
            if (emailExist || usernameExist || phoneExist) {
                if (emailExist) {
                    request.setAttribute("message", "Email này đã được sử dụng!");
                } else if (usernameExist) {
                    request.setAttribute("message", "Tên đăng nhập đã tồn tại!");
                } else if (phoneExist) {
                    request.setAttribute("message", "Số điện thoại này đã được sử dụng!");
                }
                request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
                return;
            }


            // Sinh OTP
            int otpValue = 100000 + new Random().nextInt(900000);

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

            try {
                MimeMessage message = new MimeMessage(mailSession);
                message.setFrom(new InternetAddress("hoangtvhe160642@fpt.edu.vn"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject("Xác nhận đăng ký");
                message.setText("Mã OTP của bạn là: " + otpValue + "\nOTP sẽ hết hạn sau 30 phút.");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
                request.setAttribute("message", "Không thể gửi OTP. Vui lòng thử lại!");
                request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
                return;
            }


            HttpSession session = request.getSession();
            session.setAttribute("otp", otpValue);
            session.setAttribute("username", username.trim());
            session.setAttribute("fullName", fullName.trim());
            session.setAttribute("email", email.trim());
            session.setAttribute("password", password);
            session.setAttribute("phone", phone.trim());
            session.setAttribute("actionType", "register");

            // Chuyển sang trang nhập OTP
            request.setAttribute("message", "OTP đã được gửi đến email của bạn!");
            request.getRequestDispatcher("/page/system/enterOTP.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi hệ thống. Vui lòng thử lại sau!");
            request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
        }
    }


    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isValidPassword(String password) {
        // Ít nhất 1 chữ in hoa, 1 số, 1 ký tự đặc biệt, tối thiểu 8 ký tự
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(regex);
    }


}
