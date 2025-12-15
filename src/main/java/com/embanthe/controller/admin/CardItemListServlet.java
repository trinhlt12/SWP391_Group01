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
        int page = 1, pageSize = 20;
        Integer productId = null;
        try { if (productIdStr != null) productId = Integer.parseInt(productIdStr); } catch (Exception ignored) {}
        try { if (pageStr != null) page = Integer.parseInt(pageStr); } catch (Exception ignored) {}
        if (page < 1) page = 1;

        int totalItems = cardItemDAO.count(searchSerial, searchCode, productId, status);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        if (page > totalPages && totalPages != 0) page = totalPages;
        int offset = (page - 1) * pageSize;

        List<CardItems> cardList = cardItemDAO.getPagedList(
                searchSerial, searchCode, productId, status, offset, pageSize);
        List<Products> products = productDAO.getAll();

        request.setAttribute("cardList", cardList);
        request.setAttribute("products", products);

        request.setAttribute("searchSerial", searchSerial == null ? "" : searchSerial);
        request.setAttribute("searchCode", searchCode == null ? "" : searchCode);
        request.setAttribute("status", status);
        request.setAttribute("productId", productId);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);

        request.getRequestDispatcher("/page/admin/carditemlist.jsp").forward(request, response);
    }
}