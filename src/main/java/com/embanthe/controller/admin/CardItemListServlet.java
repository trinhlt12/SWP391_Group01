package com.embanthe.controller.admin;

import com.embanthe.dao.CardItemDAO;
import com.embanthe.dao.ProductDAO;
import com.embanthe.model.CardItems;
import com.embanthe.model.Products;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/admin/carditems")
public class CardItemListServlet extends HttpServlet {
    private CardItemDAO cardItemDAO = new CardItemDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchSerial = request.getParameter("searchSerial");
        String searchCode = request.getParameter("searchCode");
        String status = request.getParameter("status");
        String productIdStr = request.getParameter("productId");
        String pageStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");

        int page = 1;
        int pageSize = 20; // default
        Integer productId = null;

        // parse productId
        try {
            if (productIdStr != null && !productIdStr.isEmpty()) productId = Integer.parseInt(productIdStr);
        } catch (Exception ignored) {}

        // parse page
        try {
            if (pageStr != null && !pageStr.isEmpty()) page = Math.max(1, Integer.parseInt(pageStr));
        } catch (Exception ignored) {}

        // parse pageSize and validate allowed values (optional)
        try {
            if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
                int ps = Integer.parseInt(pageSizeStr);
                // allow typical values only; fallback to default if invalid
                if (ps == 10 || ps == 20 || ps == 50 || ps == 100) {
                    pageSize = ps;
                }
            }
        } catch (Exception ignored) {}

        int totalItems = cardItemDAO.count(searchSerial, searchCode, productId, status);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        if (page > totalPages && totalPages != 0) page = totalPages;
        int offset = (page - 1) * pageSize;

        List<CardItems> cardList = cardItemDAO.getPagedList(
                searchSerial, searchCode, productId, status, offset, pageSize);
        List<Products> products = productDAO.getAll();

        // Build productMap for JSP lookup (if you use productMap in JSP)
        Map<Integer, Products> productMap = products.stream()
                .collect(Collectors.toMap(Products::getProductId, p -> p));

        request.setAttribute("cardList", cardList);
        request.setAttribute("products", products);
        request.setAttribute("productMap", productMap);

        request.setAttribute("searchSerial", searchSerial == null ? "" : searchSerial);
        request.setAttribute("searchCode", searchCode == null ? "" : searchCode);
        request.setAttribute("status", status);
        request.setAttribute("productId", productId);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);

        // NEW: set pageSize so JSP can use it (STT, selected option, pagination links)
        request.setAttribute("pageSize", pageSize);

        request.getRequestDispatcher("/page/admin/carditemlist.jsp").forward(request, response);
    }
}