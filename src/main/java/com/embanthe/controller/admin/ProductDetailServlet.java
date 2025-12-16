package com.embanthe.controller.admin;

import com.embanthe.dao.ProductDAO;
import com.embanthe.model.Products;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/product-detail")
public class ProductDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            ProductDAO dao = new ProductDAO();
            Products product = dao.getProductById(productId);

            if (product == null) {
                request.getSession().setAttribute("message", "Không tìm thấy sản phẩm!");
                request.getSession().setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/admin/products");
                return;
            }

            // Lấy tên category / provider bằng helper trong DAO (tránh truy xuất thuộc tính không có trên model)
            String categoryName = "-";
            String providerName = "-";
            try {
                categoryName = dao.getCategoryNameById(product.getCategoryId());
            } catch (Exception ex) {
                // giữ "-"
            }
            try {
                providerName = dao.getProviderNameById(product.getProviderId());
            } catch (Exception ex) {
                // giữ "-"
            }

            request.setAttribute("product", product);
            request.setAttribute("categoryName", categoryName);
            request.setAttribute("providerName", providerName);

            request.getRequestDispatcher("/page/admin/productdetail.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }
}