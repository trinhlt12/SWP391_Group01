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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO dao = new UserDAO();


        String keyword = request.getParameter("keyword"); // Cho cả name và email
        String role = request.getParameter("role");
        String status = request.getParameter("status");


        if (keyword != null) {
            if (keyword != null && (keyword.contains("%") || keyword.contains("_"))) {
                keyword = "___NO_RESULT_FOUND___";
            }
        }
        int pageSize = 5;
        int page = 1;
        String pageRaw = request.getParameter("page");
        try {
            if (pageRaw != null && !pageRaw.isEmpty()) {
                page = Integer.parseInt(pageRaw);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        int totalUsersFiltered = dao.countUsers(keyword, role, status);
        int totalPages = (int) Math.ceil((double) totalUsersFiltered / pageSize);
        if (page > totalPages && totalPages > 0) page = totalPages;
        if (page < 1) page = 1;


        List<Users> list = dao.searchUsersPaging(keyword, role, status, page, pageSize);

        request.setAttribute("listUser", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("role", role);
        request.setAttribute("status", status);

        request.getRequestDispatcher("/page/admin/ManagerUser.jsp").forward(request, response);
    }
}

