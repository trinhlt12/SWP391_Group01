package com.embanthe.controller.userProfile;

import com.embanthe.dao.AuthDAO;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/userprofile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy username từ session (đã lưu khi login)
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            // Nếu chưa đăng nhập thì chuyển về trang login
            response.sendRedirect("login");
            return;
        }
        UserDAO userDAO = new UserDAO();
        Users user = userDAO.getUserByUsername(username);
        if (user == null) {
            request.getRequestDispatcher("home").forward(request, response);
            return;
        }
        // Đưa user vào request attribute để hiển thị ở JSP
        request.setAttribute("user", user);
        // Forward sang trang JSP để render thông tin
        request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy username từ session
        String sessionUsername = (String) request.getSession().getAttribute("username");
        if (sessionUsername == null) {
            response.sendRedirect("login");
            return;
        }

        // Lấy dữ liệu từ form
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        if (fullName == null || fullName.trim().isEmpty()
//                || phone == null || phone.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Các trường không được để trống!");
            request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
            return;
        }
        if (phone != null) {
            phone = phone.trim();
        }
        if (phone != null && !phone.isEmpty() && !isValidVietnamPhone(phone)) {
            request.setAttribute("error", "Số điện thoại không hợp lệ! Vui lòng nhập số Việt Nam bắt đầu bằng 0 và có 10 chữ số.");
            request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
            return;
        }
        try {
            UserDAO userDAO = new UserDAO();
            AuthDAO authDAO = new AuthDAO();
            // Lấy user theo username từ session
            Users user = userDAO.getUserByUsername(sessionUsername);
            if (user == null) {
                response.sendRedirect("home");
                return;
            }

            // Kiểm tra trùng fullName
//            if (!fullName.equals(user.getFullName()) && authDAO.isFullNameExists(fullName)) {
//                request.setAttribute("error", "FullName đã tồn tại!");
//                request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
//                return;
//            }

            // Kiểm tra độ dài
            if (fullName.length() > 30) {
                request.setAttribute("error", "Họ và tên không được vượt quá 30 ký tự!");
                request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
                return;
            }


            if (!testUsingStrictRegex(email)) {
                request.setAttribute("error", "Email không hợp lệ!");
                request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
                return;
            }

            // Kiểm tra trùng phone
            if (phone != null && !phone.isEmpty()
                    && !phone.equals(user.getPhone()) && authDAO.isPhoneExists(phone)) {
                request.setAttribute("error", "Số điện thoại đã được sử dụng bởi tài khoản khác!");
                request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
                return;
            }

            // Kiểm tra trùng email
            if (!email.equals(user.getEmail()) && authDAO.isEmailExists(email)) {
                request.setAttribute("error", "Email đã tồn tại!");
                request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
                return;
            }

            // Cập nhật thông tin
            user.setFullName(fullName);
            if (phone == null || phone.isEmpty()) {
                user.setPhone(null);
            } else {
                user.setPhone(phone);
            }
            user.setEmail(email);
            boolean updated = userDAO.updateUserProfile(user);
            if (updated) {
                // Cập nhật lại session
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("success", "Đã cập nhật thông tin!");
                response.sendRedirect("userprofile");
            } else {
                request.setAttribute("error", "Không thể cập nhật thông tin!");
                request.getRequestDispatcher("page/userProfile/userProfile.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }


    private boolean isValidVietnamPhone(String phone) {
        // Cho phép số bắt đầu bằng 0 và có 10 chữ số
        String regex = "^(0\\d{9})$";
        return phone != null && phone.matches(regex);
    }

    public boolean testUsingStrictRegex(String email) {
// Regex kiểm tra email hợp lệ
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email != null && email.matches(regexPattern);
    }

}


