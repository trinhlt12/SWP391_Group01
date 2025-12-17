package com.embanthe.controller.admin;

import com.embanthe.dao.ProviderDAO;
import com.embanthe.model.Providers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/providers")
public class ProviderListServlet extends HttpServlet {
    private ProviderDAO providerDAO = new ProviderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageStr = request.getParameter("page");
        String statusStr = request.getParameter("status");

        int page = 1, pageSize = 5;
        Integer status = null;
        try { if (pageStr != null) page = Integer.parseInt(pageStr); } catch (Exception ignored) {}
        if (page < 1) page = 1;

        if (statusStr != null && (statusStr.equals("1") || statusStr.equals("0"))) {
            status = Integer.valueOf(statusStr);
        }

        int totalItems = providerDAO.count(status);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        if (page > totalPages && totalPages != 0) page = totalPages;

        int offset = (page - 1) * pageSize;

        List<Providers> list = providerDAO.getPagedList(status, offset, pageSize);

        request.setAttribute("list", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("status", statusStr);

        // Forward tới trang đúng (chỉnh đường dẫn theo vị trí JSP trong project)
        request.getRequestDispatcher("/page/admin/providerlist.jsp").forward(request, response);
    }
}