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
    private static final String APP_PASSWORD = "urqh vtvx zakn luus";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            // Kiểm tra xem ID có tồn tại không trước khi ép kiểu để tránh lỗi
            String userIdStr = request.getParameter("userId");
            if(userIdStr == null || userIdStr.isEmpty()){
                session.setAttribute("createError", "Lỗi: Không tìm thấy ID người dùng.");
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            int userId = Integer.parseInt(userIdStr);

            UserDAO userDAO = new UserDAO();
            Users user = userDAO.getUserById(userId);

            if (user == null) {
                session.setAttribute("createError", "Không tìm thấy người dùng trong hệ thống!");
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            // --- BƯỚC 1: Tạo mật khẩu mới và lưu vào DB ---
            String randomPass = PasswordUtil.generateRandomPassword(8);
            String hashedPass = PasswordUtil.hash(randomPass);

            boolean updateSuccess = userDAO.changePassword(userId, hashedPass);

            if (updateSuccess) {
                // --- BƯỚC 2: Gửi Email ---
                boolean emailSent = false;
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", HOST_NAME);
                    props.put("mail.smtp.port", TSL_PORT);

                    // QUAN TRỌNG: Thêm 2 dòng này để Google không chặn kết nối
                    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

                    Session mailSession = Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
                        }
                    });

                    Message message = new MimeMessage(mailSession);
                    message.setFrom(new InternetAddress(APP_EMAIL, "Em Ban The - Admin System")); // Tên người gửi đẹp hơn
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
                    message.setSubject("CẤP LẠI MẬT KHẨU MỚI - EMBANTHE");

                    // Nội dung email dạng HTML cho đẹp
                    String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ddd;'>"
                            + "<h3 style='color: #2c3e50;'>Xin chào " + user.getFullName() + ",</h3>"
                            + "<p>Mật khẩu của bạn đã được Admin reset thành công.</p>"
                            + "<p>Mật khẩu mới của bạn là: <b style='font-size: 18px; color: #e74c3c; background: #eee; padding: 5px;'>" + randomPass + "</b></p>"
                            + "<p><i>Vui lòng đăng nhập và đổi lại mật khẩu ngay để bảo mật thông tin.</i></p>"
                            + "</div>";

                    message.setContent(htmlContent, "text/html; charset=utf-8");

                    Transport.send(message);
                    emailSent = true;

                } catch (Exception e) {
                    e.printStackTrace(); // In lỗi ra Console để xem nếu có
                    emailSent = false;
                }

                if (emailSent) {
                    session.setAttribute("createMessage", "Thành công! Mật khẩu mới đã được gửi tới email: " + user.getEmail());
                } else {
                    // Nếu gửi mail lỗi, hiển thị pass ra màn hình để Admin copy gửi thủ công cho khách
                    session.setAttribute("createError", "Lưu DB thành công nhưng GỬI MAIL LỖI. Pass mới là: " + randomPass);
                }
            } else {
                session.setAttribute("createError", "Lỗi Database: Không thể cập nhật mật khẩu.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("createError", "Lỗi hệ thống: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/user-list");
    }


}
