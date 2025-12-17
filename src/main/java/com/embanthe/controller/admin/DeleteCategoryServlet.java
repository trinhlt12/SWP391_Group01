package com.embanthe.controller.admin;

import com.embanthe.dao.CategoryDAO;
import com.embanthe.dao.ProductDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/category/delete")
public class DeleteCategoryServlet extends HttpServlet {

    private final CategoryDAO dao = new CategoryDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            session.setAttribute("message", "ID danh mục không hợp lệ.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/category");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException ex) {
            session.setAttribute("message", "ID danh mục không hợp lệ.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/category");
            return;
        }

        if (id <= 0) {
            session.setAttribute("message", "ID danh mục không hợp lệ.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/category");
            return;
        }

        // Ràng buộc (4): Category đang được Products tham chiếu
        if (productDAO.existsProductByCategoryId(id)) {
            session.setAttribute("message", "Không thể xóa danh mục: vẫn còn sản phẩm đang thuộc danh mục này.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/category");
            return;
        }

        dao.delete(id);
        session.setAttribute("message", "Xóa danh mục thành công.");
        session.setAttribute("messageType", "success");
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }
}