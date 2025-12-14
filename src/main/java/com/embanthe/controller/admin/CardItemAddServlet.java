package com.embanthe.controller.admin;

import com.embanthe.dao.CardItemDAO;
import com.embanthe.dao.ProductDAO;
import com.embanthe.model.CardItems;
import com.embanthe.model.Products;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

@WebServlet("/admin/carditems/add")
public class CardItemAddServlet extends HttpServlet {
    private CardItemDAO cardItemDAO = new CardItemDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Products> products = productDAO.getAll();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/page/admin/carditemsadd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int productId = Integer.parseInt(request.getParameter("productId"));

        // Lấy mảng các dòng input
        String[] serials = request.getParameterValues("serialNumber[]");
        String[] codes = request.getParameterValues("cardCode[]");
        String[] exps  = request.getParameterValues("expirationDate[]");

        int added = 0;
        List<CardItems> cards = new ArrayList<>();
        if (serials != null && codes != null && serials.length == codes.length) {
            for (int i = 0; i < serials.length; i++) {
                String serial = serials[i].trim();
                String code = codes[i].trim();
                String exp = (exps != null && exps.length > i) ? exps[i] : null;
                if (!serial.isEmpty() && !code.isEmpty()) {
                    CardItems c = new CardItems();
                    c.setProductId(productId);
                    c.setSerialNumber(serial);
                    c.setCardCode(code);
                    c.setStatus("AVAILABLE");
                    c.setExpirationDate((exp == null || exp.isEmpty()) ? null : Date.valueOf(exp));
                    cards.add(c);
                }
            }
        }
        // Thêm nhiều thẻ 1 lúc
        if (!cards.isEmpty()) {
            added = cardItemDAO.addCardItems(cards);
            if (added > 0) productDAO.incrementQuantity(productId, added);
        }
        String message = (added > 0) ?
                ("Đã nhập " + added + " thẻ thành công!") :
                "Không nhập được thẻ nào!";
        List<Products> products = productDAO.getAll();
        request.setAttribute("products", products);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/page/admin/carditemsadd.jsp").forward(request, response);
    }
}