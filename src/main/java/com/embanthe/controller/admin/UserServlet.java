package com.embanthe.controller.admin;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserListServlet", urlPatterns = {"/admin/user-list"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO dao = new UserDAO();


        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int pageSize = 5;

        // 3. Gọi hàm DAO đã có sẵn
        List<Users> list = dao.getUsersPaging(page, pageSize);
        int totalUsers = dao.countUsers();

        // 4. Tính tổng số trang
        // Công thức: (totalUsers / pageSize) làm tròn lên
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        // 5. Đẩy dữ liệu sang JSP
        request.setAttribute("listUser", list);
        request.setAttribute("currentPage", page);    // Trang đang đứng
        request.setAttribute("totalPages", totalPages); // Tổng số trang để vẽ nút

        request.getRequestDispatcher("/page/admin/ManagerUser.jsp").forward(request, response);
    }
}
