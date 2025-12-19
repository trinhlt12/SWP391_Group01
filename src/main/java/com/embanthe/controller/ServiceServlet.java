package com.embanthe.controller;

import com.embanthe.dao.CategoryDAO;
import com.embanthe.dao.ProductDAO;
import com.embanthe.model.Products;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/service")
public class ServiceServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("categoryList", categoryDAO.getAll());
        List<Products> phoneCards = productDAO.getByCategoryId(1);
        List<Products> gameCards = productDAO.getByCategoryId(2);

        req.setAttribute("phoneCards", phoneCards);
        req.setAttribute("gameCards", gameCards);

        req.getRequestDispatcher("/page/public/service.jsp").forward(req, resp);
    }

}
