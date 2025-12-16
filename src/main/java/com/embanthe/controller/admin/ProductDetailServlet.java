package com.embanthe.controller.admin;

import com.embanthe.dao.ProductDAO;
import com.embanthe.model.Products;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

// File: ProductDetailServlet.java

@WebServlet("/admin/product-detail")
public class ProductDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy ID từ request
        String idParam = request.getParameter("id");

        // 2. Validate
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            // 3. Gọi DAO
            ProductDAO dao = new ProductDAO();
            Products product = dao.getProductById(productId);

            // 4. Kiểm tra kết quả
            if (product == null) {
                request.getSession().setAttribute("message", "Không tìm thấy sản phẩm!");
                request.getSession().setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/admin/products");
                return;
            }

            // 5. Đẩy data sang JSP
            request.setAttribute("product", product);
            request.getRequestDispatcher("/page/admin/productdetail.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }
}