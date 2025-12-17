package com.embanthe. controller.admin;

import com. embanthe.dao.ProviderDAO;
import com.embanthe.model.Providers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("providerName");
        String logo = request.getParameter("logoUrl");
        String statusStr = request.getParameter("status");

        Map<String, String> errors = new HashMap<>();

        if (name == null || name.trim().isEmpty()) {
            errors.put("providerName", "Tên nhà cung cấp không được để trống.");
        }

        Integer status = null;
        if (statusStr != null && (statusStr.equals("1") || statusStr.equals("0"))) {
            status = Integer.valueOf(statusStr);
        } else {
            errors.put("status", "Trạng thái không hợp lệ.");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("validateErrors", errors);
            request.setAttribute("inputProviderName", name);
            request.setAttribute("inputLogoUrl", logo);
            request.setAttribute("inputStatus", statusStr);
            request.getRequestDispatcher("/page/admin/provideradd.jsp").forward(request, response);
            return;
        }

        Providers p = new Providers();
        p.setProviderName(name.trim());
        p.setLogoUrl(logo);
        p.setStatus(status);

        providerDAO.insert(p);

        response.sendRedirect(request.getContextPath() + "/admin/providers");
    }
}