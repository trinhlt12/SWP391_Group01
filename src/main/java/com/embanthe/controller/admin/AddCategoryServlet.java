package com.embanthe.controller.admin;

import com.embanthe.dao.CategoryDAO;
import com.embanthe.model.Categories;
import com.embanthe.util.CategoryValidator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/category/add")
public class AddCategoryServlet extends HttpServlet {

    private CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/page/admin/categoryadd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("categoryName");
        String description = request.getParameter("description");

        String error = CategoryValidator.validate(name);

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("categoryName", name);
            request.setAttribute("description", description);
            request.getRequestDispatcher("/page/admin/categoryadd.jsp").forward(request, response);
            return;
        }
        Categories c = new Categories();
        c.setCategoryName(request.getParameter("categoryName"));
        c.setDescription(request.getParameter("description"));

        dao.insert(c);

        response.sendRedirect(request.getContextPath() + "/admin/category");
    }
}