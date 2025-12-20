package com.embanthe.controller.customer;

import com.embanthe.dao.SupportRequestsDAO;
import com.embanthe.model.SupportRequests;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/sendSupport")
public class SendSupportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward tới trang JSP hiển thị form hỗ trợ
        request.getRequestDispatcher("page/customer/support.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy dữ liệu từ form
        String userIdStr = request.getParameter("userId");
        String title = request.getParameter("title");
        String message = request.getParameter("message");

        int userId = 0;
        if (userIdStr != null && !userIdStr.isEmpty()) {
            userId = Integer.parseInt(userIdStr);
        }
        try {
            SupportRequestsDAO supportRequestsDAO = new SupportRequestsDAO();
            int requestCount = supportRequestsDAO.countSupportRequestsByUser(userId);
            if (requestCount >= 3) {
                request.setAttribute("msg", "Bạn chỉ được gửi tối đa 3 yêu cầu hỗ trợ trong 1 ngày.");
                request.getRequestDispatcher("page/customer/support.jsp").forward(request, response);
                return;
            }
            // Tạo đối tượng SupportRequests
            SupportRequests supportRequest = new SupportRequests();
            supportRequest.setUserId(userId);
            supportRequest.setTitle(title);
            supportRequest.setMessage(message);
            supportRequest.setStatus("Processing");
            supportRequest.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            boolean success = supportRequestsDAO.sendSupport(supportRequest);
            if (success) {
                request.setAttribute("msg", "Yêu cầu hỗ trợ đã được gửi thành công!");
            } else {
                request.setAttribute("msg", "Có lỗi xảy ra, vui lòng thử lại.");
            }
            // Forward về lại trang JSP (contact/support)
            request.getRequestDispatcher("page/customer/support.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi kết nối cơ sở dữ liệu.");
            request.getRequestDispatcher("page/customer/support.jsp").forward(request, response);
        }


    }
}
