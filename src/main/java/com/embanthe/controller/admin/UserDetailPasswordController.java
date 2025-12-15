package com.embanthe.controller.admin;
import com.embanthe.dao.UserDAO;
import com.embanthe.util.PasswordUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet(name = "UserDetailPasswordController", urlPatterns = {"/admin/user-security"})
public class UserDetailPasswordController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String idStr = request.getParameter("id");

        try {
            int userId = Integer.parseInt(idStr);

            String newPass = request.getParameter("newPass");
            String confirmPass = request.getParameter("confirmPass");

            // 2. Kiểm tra validate
            if (newPass == null || newPass.trim().isEmpty()) {
                session.setAttribute("error", "Mật khẩu không được để trống!");
                response.sendRedirect(request.getContextPath() + "/admin/user-detail?id=" + userId + "&tab=security");
                return;
            }

            if (!newPass.equals(confirmPass)) {
                session.setAttribute("error", "Xác nhận mật khẩu không khớp!");
                // Thêm &tab=security để khi load lại trang nó mở đúng tab bảo mật
                response.sendRedirect(request.getContextPath() + "/admin/user-detail?id=" + userId + "&tab=security");
                return;
            }

            // 3. Mã hóa mật khẩu
            String hashedPassword = PasswordUtil.hash(newPass);

            // 4. Gọi DAO
            UserDAO userDAO = new UserDAO();
            boolean isSuccess = userDAO.changePassword(userId, hashedPassword);

            if (isSuccess) {
                session.setAttribute("message", "Đổi mật khẩu thành công!");
            } else {
                session.setAttribute("error", "Lỗi database, không thể đổi mật khẩu.");
            }

            // Redirect và giữ ở tab security
            response.sendRedirect(request.getContextPath() + "/admin/user-detail?id=" + userId + "&tab=security");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/user-list");
        }
    }
}
