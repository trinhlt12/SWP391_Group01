package com.embanthe.servlet.accountServlet;

import jakarta.servlet.RequestDispatcher;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "ValidateOtp", urlPatterns = {"/validateOtp"})


public class EnterOtpServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int value = Integer.parseInt(request.getParameter("otp"));
        String username = request.getParameter("username");
        HttpSession session = request.getSession();
        int otp = (int) session.getAttribute("otp");

        RequestDispatcher dispatcher = null;

        if (value == otp) {

            request.setAttribute("email", request.getParameter("email"));
            request.setAttribute("status", "success");

            dispatcher = request.getRequestDispatcher("page/system/newPassword.jsp");
            dispatcher.forward(request, response);

        } else {
            request.setAttribute("message", "Sai otp");
            dispatcher = request.getRequestDispatcher("page/system/enterOtp.jsp");
            dispatcher.forward(request, response);

        }
    }

}
