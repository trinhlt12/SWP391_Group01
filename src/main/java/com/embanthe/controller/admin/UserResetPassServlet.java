package com.embanthe.controller.admin;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;
import com.embanthe.util.PasswordUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserResetPasswordNowServlet", urlPatterns = {"/admin/user-reset-pass"})
public class UserResetPassServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            int userId = Integer.parseInt(request.getParameter("id"));

            UserDAO userDAO = new UserDAO();

            Users username = userDAO.getUserById(userId);

            if (username == null) {
                session.setAttribute("error", "Không tìm thấy user.");
                response.sendRedirect(request.getContextPath() + "/admin/user-list");
                return;
            }

            String defaultPassword = username + "@123";
            String hashedPassword = PasswordUtil.hash(defaultPassword);

            boolean isSuccess = userDAO.changePassword(userId, hashedPassword);

            if (isSuccess) {
                session.setAttribute(
                        "message",
                        "Đã reset mật khẩu về mặc định cho user '" +username + "'"
                );
            } else {
                session.setAttribute("error", "Reset mật khẩu thất bại.");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/user-list");
    }
}