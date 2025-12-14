package com.embanthe.controller.admin;

import com.embanthe.dao.CategoryDAO;
import com.embanthe.dao.ProductDAO;
import com.embanthe.dao.ProviderDAO;
import com.embanthe.model.Products;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/products/add")
public class AddProductServlet extends HttpServlet {
    private ProviderDAO providerDao = new ProviderDAO();
    private CategoryDAO categoryDao = new CategoryDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("providers", providerDao.getAll());
        request.setAttribute("categories", categoryDao.getAll());

        request.getRequestDispatcher("/page/admin/addproduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Products p = new Products();
        p.setProviderId(Integer.parseInt(request.getParameter("providerId")));
        p.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        p.setProductName(request.getParameter("productName"));
        p.setPrice(Double.parseDouble(request.getParameter("price")));
        p.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        p.setImageUrl(request.getParameter("imageUrl"));

        ProductDAO productDAO = new ProductDAO();
        productDAO.insert(p);

        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}

