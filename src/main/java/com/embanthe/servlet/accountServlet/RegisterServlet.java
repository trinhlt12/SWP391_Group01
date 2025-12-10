package com.embanthe.servlet.accountServlet;

import com.embanthe.dao.AuthDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private final AuthDAO authDAO;

    public RegisterServlet() {
        try {
            this.authDAO = new AuthDAO();
        } catch (SQLException e) {
            throw new RuntimeException("Không thể kết nối database khi khởi tạo RegisterServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate đầu vào
        if (isEmpty(username) || isEmpty(fullName) || isEmpty(email) || isEmpty(phone)
                || isEmpty(password) || isEmpty(confirmPassword)) {
            request.setAttribute("message", "Vui lòng điền đầy đủ thông tin!");
            request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
            return;
        }
        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
            return;
        }

        if (!isValidPassword(password)) {
            request.setAttribute("message", "Mật khẩu phải có ít nhất 1 chữ in hoa, 1 số và 1 ký tự đặc biệt, tối thiểu 8 ký tự!");
            request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
            return;
        }

        try {
            boolean success = authDAO.register(username.trim(), fullName.trim(), email.trim(), password, phone.trim());
            if (success) {
                request.setAttribute("message", "Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.");
                request.getRequestDispatcher("/page/system/login.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Email này đã được sử dụng!");
                request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi hệ thống. Vui lòng thử lại sau!");
            request.getRequestDispatcher("/page/system/register.jsp").forward(request, response);
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isValidPassword(String password) {
        // Ít nhất 1 chữ in hoa, 1 số, 1 ký tự đặc biệt, tối thiểu 8 ký tự
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(regex);
    }
}
