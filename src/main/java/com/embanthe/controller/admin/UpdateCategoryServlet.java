package com.embanthe.controller.admin;

import com.embanthe.dao.CategoryDAO;
import com.embanthe.model.Categories;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/category/update")
public class UpdateCategoryServlet extends HttpServlet {

    private CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("category", dao.getById(id));
        request.getRequestDispatcher("/page/admin/categoryupdate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String statusStr = request.getParameter("status");
        int status = 1;
        if (statusStr != null && (statusStr.equals("0") || statusStr.equals("1"))) {
            status = Integer.parseInt(statusStr);
        }

        Categories c = new Categories();
        c.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        c.setCategoryName(request.getParameter("categoryName"));
        c.setDescription(request.getParameter("description"));
        c.setStatus(status);

        dao.update(c);

        response.sendRedirect(request.getContextPath() + "/admin/category");
    }
}