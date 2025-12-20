package com.embanthe.controller.admin;

import com.embanthe.dao.SupportRequestsDAO;
import com.embanthe.model.SupportRequests;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/support-list")
public class SupportListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            SupportRequestsDAO dao = new SupportRequestsDAO();
            UserDAO userDao = new UserDAO();

            String subject = request.getParameter("subject");
            String status = request.getParameter("status");

            // Lấy tham số phân trang
            int page = 1;
            int pageSize = 4; // số bản ghi mỗi trang
            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            // Tổng số bản ghi để tính tổng số trang
            int totalRequests = dao.countSupportRequests(subject, status);
            int totalPages = (int) Math.ceil((double) totalRequests / pageSize);

            // Kiểm tra page hợp lệ
            if (page < 1) {
                page = 1;
            }
            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }

            int offset = (page - 1) * pageSize;

            // Lấy dữ liệu theo filter + phân trang
            List<SupportRequests> requests = dao.getSupportRequests(offset, pageSize, subject, status);

            List<Users> users = userDao.getAll();

            // Gửi dữ liệu xuống JSP
            request.setAttribute("users", users);
            request.setAttribute("supportRequests", requests);
            request.setAttribute("selectedSubject", subject);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalRequests", totalRequests);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/page/admin/support-list.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
