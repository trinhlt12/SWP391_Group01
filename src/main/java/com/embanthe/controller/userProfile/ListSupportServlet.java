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
        // Lấy user từ session (object User đã được lưu khi login)
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
            // Nếu chưa login, chuyển về trang login
            response.sendRedirect("login");
            return;
        }
        int userId = user.getUserId();  // Giả sử model User có getter getUserId()

        try {
            SupportRequestsDAO dao = new SupportRequestsDAO();
            List<SupportRequests> supportRequests = dao.getAllByUser(userId);

            request.setAttribute("supportRequests", supportRequests);
            request.getRequestDispatcher("/page/userProfile/listSupport.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/page/userProfile/listSupport.jsp").forward(request, response);
        }
    }
}
