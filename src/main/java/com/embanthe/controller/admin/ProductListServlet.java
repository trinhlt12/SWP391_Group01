package com.embanthe.controller.admin;

import com.embanthe.dao.ProductDAO;
import com.embanthe.dao.CategoryDAO;
import com.embanthe.dao.ProviderDAO;
import com.embanthe.model.Categories;
import com.embanthe.model.Providers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductListServlet", value = "/admin/products")
public class ProductListServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private ProviderDAO providerDAO = new ProviderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        String categoryIdStr = request.getParameter("categoryId");
        String providerIdStr = request.getParameter("providerId");

        Integer categoryId = null, providerId = null;
        try { if (categoryIdStr != null) categoryId = Integer.parseInt(categoryIdStr); } catch (Exception ignored) {}
        try { if (providerIdStr != null) providerId = Integer.parseInt(providerIdStr); } catch (Exception ignored) {}

        int page = 1, pageSize = 10;
        try { if (pageStr != null) page = Integer.parseInt(pageStr); } catch (Exception ignored) {}
        try { if (pageSizeStr != null) pageSize = Integer.parseInt(pageSizeStr); } catch (Exception ignored) {}
        if (page < 1) page = 1;
        if (pageSize <= 0) pageSize = 10;

        int totalItems = productDAO.count(search, categoryId, providerId);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        if (page > totalPages && totalPages != 0) page = totalPages;

        int offset = (page - 1) * pageSize;
        request.setAttribute("list", productDAO.getPagedList(search, sort, categoryId, providerId, offset, pageSize));
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalItems", totalItems);

        // Đổ filter ra view (giữ giá trị filter khi submit form)
        request.setAttribute("search", (search != null) ? search : "");
        request.setAttribute("sort", sort);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("providerId", providerId);

        // Đổ list cho dropdown filter
        List<Categories> categories = categoryDAO.getAll();
        List<Providers> providers = providerDAO.getAll();
        request.setAttribute("categories", categories);
        request.setAttribute("providers", providers);

        request.getRequestDispatcher("/page/admin/productlist.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}