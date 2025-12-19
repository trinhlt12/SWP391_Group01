package com.embanthe.controller.home;
import com.embanthe.dao.HomeDAO;
import com.embanthe.model.Categories;
import com.embanthe.model.Products;
import com.embanthe.model.Providers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.embanthe.dao.ProductDAO;
import com.embanthe.dao.ProviderDAO;
import com.embanthe.dao.CategoryDAO;
import com.embanthe.model.Products;
import com.embanthe.model.Providers;
import com.embanthe.model.Categories;





@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HomeDAO dao = new HomeDAO();

        // 1. Lấy dữ liệu
        List<Categories> categories = dao.getAllCategories();
        List<Providers> providers = dao.getAllProviders();
        List<Products> products = dao.getNewestProducts();

        // 2. Đẩy sang JSP
        request.setAttribute("categoriesList", categories);
        request.setAttribute("providerList", providers);
        request.setAttribute("productList", products);

        // 3. Hiển thị
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
