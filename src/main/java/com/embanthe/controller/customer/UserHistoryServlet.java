package com.embanthe.controller.customer;

import com.embanthe.dao.TransactionDAO; // Hoặc StatisticalDAO (tùy nơi bạn viết hàm SQL)
import com.embanthe.model.Transactions;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/user-history")
public class UserHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Thiết lập tiếng Việt cho các tham số tìm kiếm
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // 2. Kiểm tra đăng nhập (Bắt buộc)
        // Nếu chưa đăng nhập thì đá về trang login
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Users user = (Users) session.getAttribute("user");

        // 3. Lấy các tham số từ Form tìm kiếm trên JSP
        String keyword = request.getParameter("keyword");
        String type = request.getParameter("type");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        // 4. Xử lý phân trang
        int page = 1;
        int pageSize = 10; // Mỗi trang hiện 10 dòng
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        try {
            // Gọi DAO (Nơi chứa các hàm SQL mình đã viết ở bước trước)
            TransactionDAO dao = new TransactionDAO();

            // a. Lấy thống kê tổng quan (Tổng nạp, Tổng tiêu)
            Map<String, Double> overview = dao.getUserOverview(user.getUserId());

            // b. Lấy danh sách giao dịch (Có lọc + Phân trang)
            List<Transactions> historyList = dao.getUserTransactions(user.getUserId(), keyword, type, fromDate, toDate, page, pageSize);

            // c. Tính tổng số trang
            int totalRecords = dao.countUserTransactions(user.getUserId(), keyword, type, fromDate, toDate);
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

            // 5. Gửi dữ liệu sang JSP
            request.setAttribute("overview", overview);
            request.setAttribute("historyList", historyList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);


            request.setAttribute("paramKeyword", keyword);
            request.setAttribute("paramType", type);
            request.setAttribute("paramFromDate", fromDate);
            request.setAttribute("paramToDate", toDate);


            request.getRequestDispatcher("/user_history.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi kết nối cơ sở dữ liệu");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}