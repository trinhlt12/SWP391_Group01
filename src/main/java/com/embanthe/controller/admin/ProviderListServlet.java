package com.embanthe.controller.admin;

import com.embanthe.dao. ProviderDAO;
import com. embanthe.model.Providers;

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
        String pageStr = request. getParameter("page");
        String search = request.getParameter("search");

        int page = 1, pageSize = 10;
        try { if (pageStr != null) page = Integer.parseInt(pageStr); } catch (Exception ignored) {}
        if (page < 1) page = 1;

        int totalItems = providerDAO.count(search);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        if (page > totalPages && totalPages != 0) page = totalPages;

        int offset = (page - 1) * pageSize;
        List<Providers> list = providerDAO. getPagedList(search, offset, pageSize);

        request.setAttribute("list", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("search", search == null ? "" : search);

        request.getRequestDispatcher("/page/admin/providerlist.jsp").forward(request, response);
    }
}