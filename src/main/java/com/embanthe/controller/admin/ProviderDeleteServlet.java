package com. embanthe.controller.admin;

import com.embanthe. dao.ProviderDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/providers/delete")
public class ProviderDeleteServlet extends HttpServlet {
    private ProviderDAO providerDAO = new ProviderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        providerDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/providers");
    }
}