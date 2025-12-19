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

@WebServlet("/review-order")
public class ReviewOrderServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?message=" + java.net.URLEncoder.encode("Vui lòng đăng nhập", "UTF-8"));
            return;
        }

        try {
            String productIdStr = req.getParameter("productId");
            String quantityStr = req.getParameter("quantity");

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