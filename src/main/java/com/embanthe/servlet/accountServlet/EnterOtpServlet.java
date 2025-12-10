package com.embanthe.servlet.accountServlet;

import com.embanthe.dao.AuthDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ValidateOtp", urlPatterns = {"/validateOtp"})
public class EnterOtpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int enteredOtp = Integer.parseInt(request.getParameter("otp"));
        HttpSession session = request.getSession();
        int otp = (int) session.getAttribute("otp");
        String actionType = (String) session.getAttribute("actionType");

        RequestDispatcher dispatcher;

        if (enteredOtp == otp) {
            if ("forgotPassword".equals(actionType)) {
                // OTP đúng cho quên mật khẩu
                request.setAttribute("email", session.getAttribute("email"));
                request.setAttribute("status", "success");
                dispatcher = request.getRequestDispatcher("page/system/newPassword.jsp");
                dispatcher.forward(request, response);

            } else if ("register".equals(actionType)) {
                // OTP đúng cho đăng ký
                String username = (String) session.getAttribute("username");
                String fullName = (String) session.getAttribute("fullName");
                String email = (String) session.getAttribute("email");
                String password = (String) session.getAttribute("password");
                String phone = (String) session.getAttribute("phone");

                try {
                    AuthDAO authDAO = new AuthDAO();
                    boolean success = authDAO.register(username, fullName, email, password, phone);
                    if (success) {
                        request.setAttribute("message", "Đăng ký thành công! Bạn có thể đăng nhập.");
                        dispatcher = request.getRequestDispatcher("page/system/login.jsp");
                    } else {
                        request.setAttribute("message", "Email đã tồn tại!");
                        dispatcher = request.getRequestDispatcher("page/system/register.jsp");
                    }
                    dispatcher.forward(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("message", "Lỗi hệ thống!");
                    request.getRequestDispatcher("page/system/register.jsp").forward(request, response);
                }
            }
        } else {
            request.setAttribute("message", "Sai OTP!");
            request.setAttribute("email", session.getAttribute("email"));
            dispatcher = request.getRequestDispatcher("page/system/enterOTP.jsp");
            dispatcher.forward(request, response);
        }
    }
}
