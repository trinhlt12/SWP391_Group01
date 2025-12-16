package com.embanthe.controller.home;
import java.io.IOException;
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
        ProductDAO productDAO = new ProductDAO();
        List<Products> productList = productDAO.getAll();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Categories> categoriesList = categoryDAO.getAll();
        ProviderDAO providerDAO = new ProviderDAO();
        List<Providers> providerList = providerDAO.getAll();
        request.setAttribute("productList", productList);
        request.setAttribute("categoriesList", categoriesList);
        request.setAttribute("providerList", providerList);
        request.setAttribute("currentPage", "home");
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
