package com.embanthe.controller.admin;

import com.embanthe.dao.CategoryDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/category")
public class CategoryListServlet extends HttpServlet {

    private CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("list", dao.getAll());
        request.getRequestDispatcher("/page/admin/categorylist.jsp")
                .forward(request, response);

    }
}