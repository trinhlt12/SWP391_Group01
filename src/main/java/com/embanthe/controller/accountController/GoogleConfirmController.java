package com.embanthe.controller.accountController;

import com.embanthe.dao.AuthDAO;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/googleConfirm")
public class GoogleConfirmController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Khi redirect bằng GET tới /googleConfirm, forward sang trang nhập mật khẩu
            request.getRequestDispatcher("page/system/googlePassword.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Lỗi khi load trang xác nhận Google", e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");


            if (password == null || !password.equals(confPassword)) {
                request.setAttribute("message", "Mật khẩu nhập lại không khớp!");
                request.getRequestDispatcher("page/system/googlePassword.jsp").forward(request, response);
                return;
            }
            if (!isValidPassword(password)) {
                request.setAttribute("message", "Mật khẩu phải >=8 ký tự, có chữ hoa, số và ký tự đặc biệt!");
                request.getRequestDispatcher("page/system/googlePassword.jsp").forward(request, response);
                return;
            }
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                // Hash mật khẩu mới
                String hashed = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());

                userDAO.changePassword(user.getUserId(), hashed);

                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("email", user.getEmail());
                session.setAttribute("googleConfirmed", true);
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                } else {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
            } else {
                request.setAttribute("message", "Không tìm thấy user Google!");
                request.getRequestDispatcher("page/system/googlePassword.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);

        }
    }
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(regex);
    }
}
