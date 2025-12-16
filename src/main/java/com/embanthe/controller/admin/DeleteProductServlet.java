package com.embanthe.controller.admin;

import com.embanthe.dao.ProductDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/products/delete")
public class DeleteProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            session.setAttribute("message", "ID sản phẩm không hợp lệ.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/products");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            session.setAttribute("message", "ID sản phẩm không hợp lệ.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/products");
            return;
        }

        try {
            // Kiểm tra chi tiết
            int availableCount = productDAO.countCardItemsByStatus(id, "AVAILABLE");
            int soldCount = productDAO.countCardItemsByStatus(id, "SOLD");
            int completedOrders = productDAO.countOrdersByStatus(id, "COMPLETED");
            int pendingOrders = productDAO.countOrdersByStatus(id, "PENDING"); // tuỳ hệ thống

            if (availableCount > 0) {
                session.setAttribute("message", "Không thể xóa: hiện có " + availableCount + " thẻ còn trong kho (AVAILABLE)");
                session.setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/admin/products");
                return;
            }

            if (soldCount > 0 || completedOrders > 0) {
                StringBuilder sb = new StringBuilder("Không thể xóa: ");
                if (soldCount > 0) sb.append("có ").append(soldCount).append(" thẻ đã bán (SOLD). ");
                if (completedOrders > 0) sb.append("sản phẩm đã xuất hiện trong ").append(completedOrders).append(" đơn hàng đã hoàn thành. ");
                session.setAttribute("message", sb.toString());
                session.setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/admin/products");
                return;
            }

            if (pendingOrders > 0) {
                session.setAttribute("message", "Không thể xóa: có " + pendingOrders + " đơn hàng đang xử lý liên quan. Vui lòng xử lý các đơn trước.");
                session.setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/admin/products");
                return;
            }

            // Nếu qua hết kiểm tra -> xóa được
            boolean deleted = productDAO.delete(id);
            if (deleted) {
                session.setAttribute("message", "Xóa sản phẩm thành công.");
                session.setAttribute("messageType", "success");
            } else {
                session.setAttribute("message", "Không thể xóa sản phẩm do lỗi hệ thống hoặc ràng buộc khác.");
                session.setAttribute("messageType", "error");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("message", "Lỗi hệ thống khi kiểm tra/xóa sản phẩm. Vui lòng thử lại hoặc kiểm tra log.");
            session.setAttribute("messageType", "error");
        }

        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}