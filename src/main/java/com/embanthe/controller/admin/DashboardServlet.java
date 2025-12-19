package com.embanthe.controller.admin;

import com.embanthe.dao.StatisticalDAO;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set encoding để nhận tiếng Việt từ form search
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // 1. Kiểm tra đăng nhập
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Users user = (Users) session.getAttribute("user");

        // 2. Kiểm tra quyền Admin
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {

            try {
                StatisticalDAO dao = new StatisticalDAO();

                // === A. SỐ LIỆU TỔNG QUAN & BIỂU ĐỒ (Không bị ảnh hưởng bởi bộ lọc bảng) ===
                int totalUsers = dao.countTotalUsers();
                double totalRevenue = dao.getTotalRevenue();
                int transactionsToday = dao.countTransactionsToday();

                Map<String, Double> revenueChart = dao.getRevenueLast7Days();
                Map<String, Integer> ordersChart = dao.getOrdersLast7Days();

                // === B. XỬ LÝ LỌC & PHÂN TRANG CHO BẢNG HOẠT ĐỘNG ===

                // 1. Lấy tham số tìm kiếm từ URL
                String keyword = request.getParameter("keyword");
                String type = request.getParameter("type");
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");

                // 2. Lấy tham số phân trang
                int page = 1;
                int pageSize = 10;
                String pageStr = request.getParameter("page");

                if (pageStr != null && !pageStr.isEmpty()) {
                    try {
                        page = Integer.parseInt(pageStr);
                        if (page < 1) page = 1;
                    } catch (NumberFormatException e) {
                        page = 1;
                    }
                }

                // 3. Gọi DAO với đầy đủ tham số lọc
                List<StatisticalDAO.AdminActivity> recentActivities = dao.getRecentActivities(keyword, type, fromDate, toDate, page, pageSize);

                // 4. Tính tổng số trang (Dựa trên kết quả đã lọc)
                int totalRecords = dao.getTotalActivities(keyword, type, fromDate, toDate);
                int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

                // === C. ĐẨY DỮ LIỆU SANG JSP ===

                // Dữ liệu Dashboard
                request.setAttribute("totalUsers", totalUsers);
                request.setAttribute("totalRevenue", totalRevenue);
                request.setAttribute("transactionsToday", transactionsToday);
                request.setAttribute("revenueMap", revenueChart);
                request.setAttribute("ordersMap", ordersChart);

                // Dữ liệu Bảng & Phân trang
                request.setAttribute("recentActivities", recentActivities);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                // QUAN TRỌNG: Gửi lại tham số lọc để JSP hiển thị lại trên Form và Link phân trang
                request.setAttribute("paramKeyword", keyword);
                request.setAttribute("paramType", type);
                request.setAttribute("paramFromDate", fromDate);
                request.setAttribute("paramToDate", toDate);

                // Forward sang trang giao diện
                request.getRequestDispatcher("/page/admin/dashboard.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi lấy dữ liệu thống kê");
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}