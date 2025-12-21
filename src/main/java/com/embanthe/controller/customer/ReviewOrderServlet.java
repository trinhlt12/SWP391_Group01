package com.embanthe.controller.customer;

import com.embanthe.dao.ProductDAO;
import com.embanthe.model.Products;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/review-order")
public class ReviewOrderServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");

        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");

        if (productIdStr == null && session.getAttribute("PENDING_PRODUCT_ID") != null) {
            productIdStr = (String) session.getAttribute("PENDING_PRODUCT_ID");
            quantityStr = (String) session.getAttribute("PENDING_QUANTITY");

            session.removeAttribute("PENDING_PRODUCT_ID");
            session.removeAttribute("PENDING_QUANTITY");
        }

        if (user == null) {
            if (productIdStr != null && quantityStr != null) {
                session.setAttribute("PENDING_PRODUCT_ID", productIdStr);
                session.setAttribute("PENDING_QUANTITY", quantityStr);
            }

            String message = "";
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());

            resp.sendRedirect(req.getContextPath() + "/login?message=" + encodedMessage + "&redirect=review-order");

            return;
        }

        try {
            // Validate
            if (productIdStr == null || quantityStr == null) {
                resp.sendRedirect(req.getContextPath() + "/service");
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            Products product = productDAO.getProductById(productId);
            if (product == null) {
                req.setAttribute("errorMessage", "Sản phẩm không tồn tại");
                req.getRequestDispatcher("/service").forward(req, resp);
                return;
            }

            double totalAmount = product.getPrice() * quantity;

            req.setAttribute("product", product);
            req.setAttribute("quantity", quantity);
            req.setAttribute("totalAmount", totalAmount);

            req.getRequestDispatcher("/page/customer/review-order.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/service");
        }

    }
}