package com.embanthe.controller.product;

import com.embanthe.dao.ProductDAO;
import com.embanthe.model.Products;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet hiển thị danh sách sản phẩm cho admin
 */
@WebServlet(name = "ProductListServlet", urlPatterns = {"/products"})
public class ProductListServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        try {
            productDAO = new ProductDAO();
        } catch (SQLException e) {
            throw new ServletException("Cannot initialize ProductDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        String sort = req.getParameter("sort");
        String categoryParam = req.getParameter("category");

        Long categoryId = null;
        if (categoryParam != null && !categoryParam.isEmpty()) {
            try {
                categoryId = Long.parseLong(categoryParam);
            } catch (NumberFormatException ignored) { }
        }

        int page = 1;
        int size = 10;
        try {
            String pageParam = req.getParameter("page");
            if (pageParam != null) page = Integer.parseInt(pageParam);
            String sizeParam = req.getParameter("size");
            if (sizeParam != null) size = Integer.parseInt(sizeParam);
        } catch (NumberFormatException ignored) { }

        try {
            int total = productDAO.countProducts(q, categoryId);
            int totalPages = (int) Math.ceil((double) total / size);
            if (totalPages == 0) totalPages = 1;
            if (page < 1) page = 1;
            if (page > totalPages) page = totalPages;

            List<Products> products = productDAO.listProducts(page, size, q, categoryId, sort);

            req.setAttribute("products", products);
            req.setAttribute("page", page);
            req.setAttribute("size", size);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("q", q);
            req.setAttribute("sort", sort);
            req.setAttribute("category", categoryId);

            // forward tới JSP admin
            req.getRequestDispatcher("/page/admin/productlistview.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}