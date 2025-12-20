package com.embanthe.controller.admin;

import com.embanthe.dao.SupportRequestsDAO;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.SupportRequests;
import com.embanthe.model.Users;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@WebServlet("/admin/support-detail")
public class SupportDetailServlet extends HttpServlet {

    private final SupportRequestsDAO supportDAO = new SupportRequestsDAO();
    private final UserDAO userDAO = new UserDAO();

    public SupportDetailServlet() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra quyền admin (giả sử có HttpSession với attribute "admin")
        HttpSession session = req.getSession();
        String mes = (String) req.getSession().getAttribute("mes");
        if (mes != null) {
            req.setAttribute("mes", mes);
            req.getSession().removeAttribute("mes"); // xóa để không hiện lại lần sau
        }

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            req.setAttribute("mes", "Support request không tồn tại hoặc đã bị xóa.");
            req.getRequestDispatcher("/page/admin/support-detail.jsp").forward(req, resp);
            return;
        }

        int requestId;
        try {
            requestId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new ServletException("Database error", e);
        }

        try {
            SupportRequests supportRequest = supportDAO.getById(requestId);
            if (supportRequest == null) {
                req.setAttribute("mes", "Support request không tồn tại hoặc đã bị xóa.");
                req.getRequestDispatcher("/page/admin/support-detail.jsp").forward(req, resp);
                return;
            }

            // Chỉ lấy đúng user cần thiết thay vì toàn bộ
            Users user = userDAO.getUserById((int) supportRequest.getUserId());
            String username = (user != null) ? user.getUsername() : "Unknown";

            req.setAttribute("supportRequest", supportRequest);
            req.setAttribute("username", username); // truyền trực tiếp

            req.getRequestDispatcher("/page/admin/support-detail.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        int requestId = Integer.parseInt(req.getParameter("requestId"));
        String processNote = req.getParameter("processNote");
        String status = req.getParameter("status"); // Gọi DAO để update
        HttpSession session = req.getSession();
        try {
            SupportRequestsDAO dao = new SupportRequestsDAO();
            SupportRequests supportRequest = dao.getById(requestId);
            // Nếu đã có processNote rồi thì không cho nhập lại

            if (supportRequest.getProcessNote() != null && !supportRequest.getProcessNote().isEmpty()) {
                req.setAttribute("mes", "Support request đã được xử lý, không thể nhập lại.");
                req.getRequestDispatcher("/page/admin/support-detail.jsp").forward(req, resp);
                return;
            }
            if (processNote == null || processNote.trim().isEmpty()) {
                req.setAttribute("mes", "Bạn phải nhập ghi chú xử lý trước khi gửi.");
                req.setAttribute("supportRequest", supportRequest);
                req.getRequestDispatcher("/page/admin/support-detail.jsp").forward(req, resp);
                return;
            }// Nếu admin nhập processNote thì status phải Approved hoặc Rejected
            if (processNote != null && !processNote.trim().isEmpty()) {
                if (!"Approved".equals(status) && !"Rejected".equals(status)) {
                    req.setAttribute("mes", "Khi nhập ghi chú xử lý, trạng thái phải là Approved hoặc Rejected.");
                    req.setAttribute("supportRequest", supportRequest);
                    // giữ nguyên object
                    req.getRequestDispatcher("/page/admin/support-detail.jsp").forward(req, resp);
                    return;
                }
            }
            // Thực hiện update
            dao.updateProcess(requestId, processNote, status);
            session.setAttribute("mes", "Cập nhật thành công");
            Users user = userDAO.getUserById((int) supportRequest.getUserId());
            if (user != null && user.getEmail() != null) {
                new Thread(() -> {
                    try {
                        Properties props = new Properties();
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.port", "587");
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable", "true");
                        Session mailSession = Session.getInstance(props, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication("hoangtvhe160642@fpt.edu.vn", "nxco yzoq vdrn upqv");
                            }
                        });
                        Message message = new MimeMessage(mailSession);
                        message.setFrom(new InternetAddress("hoangtvhe160642@fpt.edu.vn"));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
                        message.setSubject("Kết quả xử lý yêu cầu hỗ trợ #" + requestId);
                        message.setText("Xin chào " + user.getUsername() + ",\n\n"
                                + "Yêu cầu hỗ trợ của bạn đã được xử lý.\n"
                                +"Vui lòng xem kết quả tại trang web " + "\n"
                                + "Trân trọng.");
                        Transport.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            resp.sendRedirect("support-detail?id=" + requestId);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

