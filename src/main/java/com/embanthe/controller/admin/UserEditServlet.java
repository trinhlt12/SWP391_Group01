package com.embanthe.controller.admin;

import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserEditServlet", urlPatterns = {"/admin/user-edit"})
public class UserEditServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/admin/user-list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // Lấy dữ liệu
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");
            String status = request.getParameter("status");

            // Tạo User object
            Users user = Users.builder()
                    .userId(id)
                    .fullName(fullName)
                    .email(email)
                    .role(role)
                    .phone(phone)
                    .status(status)
                    .build();

            // Gọi DAO update
            UserDAO dao = new UserDAO();
            boolean isSuccess = dao.updateUser(user);

            if (isSuccess) {
                response.sendRedirect(request.getContextPath() + "/admin/user-list?msg=Success");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/user-list?msg=Fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/user-list?msg=Error");
        }
    }
}