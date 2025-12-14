package com.embanthe.controller.adminController;

import com.embanthe.dao.UserDAO;
import com.embanthe.util.PasswordUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserResetPasswordNowServlet", urlPatterns = {"/admin/user-reset-pass"})
public class UserResetPassController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            // 1. Lấy dữ liệu từ form
            String idStr = request.getParameter("id");
            String newPass = request.getParameter("newPass");

            // Validate cơ bản
            if (newPass == null || newPass.trim().isEmpty()) {
                session.setAttribute("error", "Mật khẩu không được để trống!");
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            int userId = Integer.parseInt(idStr);

            String passwordToSave = PasswordUtil.hash(newPass);

            UserDAO userDAO = new UserDAO();

            boolean isSuccess = userDAO.updatePassword(userId, passwordToSave);

            if (isSuccess) {
                session.setAttribute("message", "Đã đổi mật khẩu thành công cho user #" + userId);
            } else {
                session.setAttribute("error", "Lỗi: Không tìm thấy User hoặc lỗi Database.");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID người dùng không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi xử lý hệ thống.");
        }

        // 5. Quay về trang danh sách
        response.sendRedirect(request.getContextPath() + "/admin/user-list");
    }
}