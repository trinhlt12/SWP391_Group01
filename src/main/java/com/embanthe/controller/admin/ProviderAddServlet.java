package com.embanthe. controller.admin;

import com. embanthe.dao.ProviderDAO;
import com.embanthe.model.Providers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/providers/add")
public class ProviderAddServlet extends HttpServlet {
    private ProviderDAO providerDAO = new ProviderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/page/admin/provideradd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request. setCharacterEncoding("UTF-8");
        String name = request.getParameter("providerName");
        String logo = request.getParameter("logoUrl");

        Providers p = new Providers();
        p.setProviderName(name);
        p.setLogoUrl(logo);
        providerDAO.insert(p);

        response.sendRedirect(request.getContextPath() + "/admin/providers");
    }
}