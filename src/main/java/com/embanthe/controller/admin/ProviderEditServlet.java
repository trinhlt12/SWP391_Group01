package com. embanthe.controller.admin;

import com.embanthe. dao.ProviderDAO;
import com.embanthe.model.Providers;

import javax. servlet.*;
import javax.servlet. http.*;
import javax.servlet.annotation.*;
import java.io. IOException;

@WebServlet("/admin/providers/edit")
public class ProviderEditServlet extends HttpServlet {
    private ProviderDAO providerDAO = new ProviderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Providers p = providerDAO.getById(id);
        request.setAttribute("provider", p);
        request.getRequestDispatcher("/page/admin/provideredit. jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("providerId"));
        String name = request. getParameter("providerName");
        String logo = request.getParameter("logoUrl");

        Providers p = new Providers();
        p.setProviderId(id);
        p.setProviderName(name);
        p.setLogoUrl(logo);
        providerDAO.update(p);

        response.sendRedirect(request.getContextPath() + "/admin/providers");
    }
}