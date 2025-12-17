package com.embanthe.controller.admin;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;
import com.embanthe.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@WebServlet(name = "UserResetPasswordNowServlet", urlPatterns = {"/admin/user-reset-pass"})
public class UserResetPassServlet extends HttpServlet {

    private static final String HOST_NAME = "smtp.gmail.com";
    private static final int TSL_PORT = 587;
    private static final String APP_EMAIL = "hungdshe172854@fpt.edu.vn";
    private static final String APP_PASSWORD = "urqh vtvx zakn luus"; // app password

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            String userIdStr = request.getParameter("userId");
            if (userIdStr == null || userIdStr.isEmpty()) {
                session.setAttribute("createError", "Không tìm thấy ID người dùng.");
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            int userId = Integer.parseInt(userIdStr);

            UserDAO userDAO = new UserDAO();
            Users user = userDAO.getUserById(userId);

            if (user == null) {
                session.setAttribute("createError", "Không tìm thấy người dùng.");
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            // 1. Generate & save new password
            String randomPass = PasswordUtil.generateRandomPassword(8);
            String hashedPass = PasswordUtil.hash(randomPass);

            boolean updateSuccess = userDAO.changePassword(userId, hashedPass);

            if (!updateSuccess) {
                session.setAttribute("createError", "Không thể cập nhật mật khẩu trong database.");
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            // 2. Send email
            boolean emailSent = false;

            try {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", HOST_NAME);
                props.put("mail.smtp.port", TSL_PORT);
                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                props.put("mail.smtp.ssl.protocols", "TLSv1.2");

                Session mailSession = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
                    }
                });

                Message message = new MimeMessage(mailSession);

                // ✅ FROM (encode UTF-8)
                message.setFrom(new InternetAddress(
                        APP_EMAIL,
                        MimeUtility.encodeText("Em Ban The - Admin System", "UTF-8", "B")
                ));

                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(user.getEmail())
                );

                // ✅ SUBJECT (encode UTF-8)
                message.setSubject(
                        MimeUtility.encodeText(
                                "CẤP LẠI MẬT KHẨU MỚI - EMBANTHE",
                                "UTF-8",
                                "B"
                        )
                );

                // HTML content
                String htmlContent =
                        "<div style='font-family: Arial, sans-serif; padding:20px;'>"
                                + "<h3>Xin chào " + user.getFullName() + ",</h3>"
                                + "<p>Mật khẩu của bạn đã được Admin cấp lại.</p>"
                                + "<p>Mật khẩu mới: "
                                + "<b style='color:red;font-size:18px'>" + randomPass + "</b></p>"
                                + "<p>Vui lòng đăng nhập và đổi lại mật khẩu ngay.</p>"
                                + "</div>";

                message.setContent(htmlContent, "text/html; charset=UTF-8");

                Transport.send(message);
                emailSent = true;

            } catch (Exception mailEx) {
                mailEx.printStackTrace();
            }

            if (emailSent) {
                session.setAttribute(
                        "createMessage",
                        "Reset mật khẩu thành công. Email đã gửi tới: " + user.getEmail()
                );
            } else {
                session.setAttribute(
                        "createError",
                        "Reset thành công nhưng gửi mail lỗi. Mật khẩu mới: " + randomPass
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("createError", "Lỗi hệ thống: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/user-list");
    }
}
