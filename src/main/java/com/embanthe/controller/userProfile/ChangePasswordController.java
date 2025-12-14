package com.embanthe.controller.userProfile;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/changePassword")
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");

        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);

            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }

            request.setAttribute("user", user);
            request.getRequestDispatcher("/page/userProfile/changePassword.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);

            if (user == null) {
                response.sendRedirect("home");
                return;
            }

            // Kiểm tra dữ liệu rỗng
            if (isEmpty(oldPassword) || isEmpty(newPassword) || isEmpty(confirmPassword)) {
                request.setAttribute("mess", "Không được để trống các trường!");
                request.getRequestDispatcher("/page/userProfile/changePassword.jsp").forward(request, response);
                return;
            }

            // Kiểm tra mật khẩu cũ
            if (!BCrypt.checkpw(oldPassword, user.getPasswordHash())) {
                request.setAttribute("mess", "Mật khẩu cũ không đúng!");
                request.getRequestDispatcher("/page/userProfile/changePassword.jsp").forward(request, response);
                return;
            }

            // Kiểm tra mật khẩu mới và confirm
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("mess", "Xác nhận mật khẩu mới không khớp!");
                request.getRequestDispatcher("/page/userProfile/changePassword.jsp").forward(request, response);
                return;
            }

            // Kiểm tra độ mạnh mật khẩu mới
            if (!isValidPassword(newPassword)) {
                request.setAttribute("mess", "Mật khẩu mới phải có ít nhất 8 ký tự, gồm 1 chữ in hoa, 1 số và 1 ký tự đặc biệt!");
                request.getRequestDispatcher("/page/userProfile/changePassword.jsp").forward(request, response);
                return;
            }

            // Hash mật khẩu mới
            String passwordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));

            // Update DB theo user_id
            boolean updated = userDAO.changePassword(user.getUserId(), passwordHash);

            if (updated) {
                request.setAttribute("success", "Đổi mật khẩu thành công!");
            } else {
                request.setAttribute("mess", "Không thể đổi mật khẩu!");
            }
            request.getRequestDispatcher("/page/userProfile/changePassword.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    // Hàm kiểm tra chuỗi rỗng
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    // Hàm kiểm tra độ mạnh mật khẩu
    private boolean isValidPassword(String password) {
        // Ít nhất 1 chữ in hoa, 1 số, 1 ký tự đặc biệt, tối thiểu 8 ký tự
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(regex);
    }
}
