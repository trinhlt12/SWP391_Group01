package com.embanthe.controller.admin;

import com.embanthe.dao.CategoryDAO;
import com.embanthe.dao.ProductDAO;
import com.embanthe.dao.ProviderDAO;
import com.embanthe.model.Products;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet("/admin/products/add")
@MultipartConfig
public class AddProductServlet extends HttpServlet {
    private ProviderDAO providerDao = new ProviderDAO();
    private CategoryDAO categoryDao = new CategoryDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("listProvider", providerDao.getAll());
        request.setAttribute("listCategory", categoryDao.getAll());
        request.getRequestDispatcher("/page/admin/addproduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> errors = new HashMap<>();
        String productName = request.getParameter("productName");
        String categoryIdStr = request.getParameter("categoryId");
        String providerIdStr = request.getParameter("providerId");
        String priceStr = request.getParameter("price");

        // Validate product name
        if (productName == null || productName.trim().isEmpty()) {
            errors.put("productName", "Product name is required.");
        }

        // Validate category
        int categoryId = -1;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
            if (categoryId <= 0) errors.put("categoryId", "Category is required.");
        } catch (Exception e) {
            errors.put("categoryId", "Category is required.");
        }

        // Validate provider
        int providerId = -1;
        try {
            providerId = Integer.parseInt(providerIdStr);
            if (providerId <= 0) errors.put("providerId", "Provider is required.");
        } catch (Exception e) {
            errors.put("providerId", "Provider is required.");
        }

        // Validate price
        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) errors.put("price", "Price must be greater than 0.");
        } catch (Exception e) {
            errors.put("price", "Invalid price value.");
        }

        // Validate image (optional: chỉ cho phép jpg, jpeg, png)
        Part filePart = request.getPart("image");
        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            String originalName = filePart.getSubmittedFileName();
            if (!originalName.matches(".*\\.(jpg|jpeg|png)$")) {
                errors.put("image", "Only .jpg, .jpeg, .png files allowed.");
            }
        }

        if (!errors.isEmpty()) {
            request.setAttribute("validateErrors", errors);
            request.setAttribute("inputProductName", productName);
            request.setAttribute("inputCategoryId", categoryIdStr);
            request.setAttribute("inputProviderId", providerIdStr);
            request.setAttribute("inputPrice", priceStr);
            request.setAttribute("listProvider", providerDao.getAll());
            request.setAttribute("listCategory", categoryDao.getAll());
            request.getRequestDispatcher("/page/admin/addproduct.jsp").forward(request, response);
            return;
        }

        // Xử lý upload ảnh (nếu hợp lệ)
        if (filePart != null && filePart.getSize() > 0) {
            String originalName = filePart.getSubmittedFileName();
            String ext = "";
            int dot = originalName.lastIndexOf('.');
            if (dot >= 0) ext = originalName.substring(dot);
            fileName = UUID.randomUUID() + ext;
            String uploadDir = request.getServletContext().getRealPath("/image");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            filePart.write(uploadDir + File.separator + fileName);
        }

        // Tạo Products
        Products product = new Products();
        product.setProductName(productName);
        product.setCategoryId(categoryId);
        product.setProviderId(providerId);
        product.setPrice(price);
        product.setQuantity(0);
        product.setImageUrl(fileName);

        productDAO.insert(product);

        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}