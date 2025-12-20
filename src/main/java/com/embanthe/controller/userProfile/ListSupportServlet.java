package com.embanthe.controller.userProfile;

import com.embanthe.dao.SupportRequestsDAO;
import com.embanthe.model.SupportRequests;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet("/listSupport")
public class ListSupportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        int userId = user.getUserId();

        try {
            int page = 1;
            int pageSize = 3;
            SupportRequestsDAO dao = new SupportRequestsDAO();

            // Lấy tham số status từ request (nếu có)
            String status = request.getParameter("status");

            // Đếm tổng số bản ghi theo userId + status
            int totalRequests = dao.countByUserAndStatus(userId, status);
            int totalPages = (int) Math.ceil((double) totalRequests / pageSize);

            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (page < 1) page = 1;
            if (page > totalPages && totalPages > 0) page = totalPages;

            int offset = (page - 1) * pageSize;

            // Lấy dữ liệu theo userId + status + phân trang
            List<SupportRequests> supportRequests = dao.getByUserWithPagingAndStatus(userId, status, offset, pageSize);

            // Gửi dữ liệu xuống JSP
            request.setAttribute("supportRequests", supportRequests);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalRequests", totalRequests);
            request.setAttribute("selectedStatus", status); // để JSP biết đang lọc theo gì

            request.getRequestDispatcher("/page/userProfile/listSupport.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/page/userProfile/listSupport.jsp").forward(request, response);
        }
    }
}

