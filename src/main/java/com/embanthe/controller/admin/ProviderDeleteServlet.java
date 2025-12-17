package com. embanthe.controller.admin;

import com.embanthe.dao.ProductDAO;
import com.embanthe.dao.ProviderDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin/providers/delete")
public class ProviderDeleteServlet extends HttpServlet {
    private final ProviderDAO providerDAO = new ProviderDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            session.setAttribute("message", "ID nhà cung cấp không hợp lệ.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/providers");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException ex) {
            session.setAttribute("message", "ID nhà cung cấp không hợp lệ.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/providers");
            return;
        }

        if (id <= 0) {
            session.setAttribute("message", "ID nhà cung cấp không hợp lệ.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/providers");
            return;
        }

        // Ràng buộc (5): Provider đang được Products tham chiếu
        if (productDAO.existsProductByProviderId(id)) {
            session.setAttribute("message", "Không thể xóa nhà cung cấp: vẫn còn sản phẩm đang thuộc nhà cung cấp này.");
            session.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/providers");
            return;
        }

        providerDAO.delete(id);
        session.setAttribute("message", "Xóa nhà cung cấp thành công.");
        session.setAttribute("messageType", "success");
        response.sendRedirect(request.getContextPath() + "/admin/providers");
    }
}