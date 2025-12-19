    package com.embanthe.controller.admin;

    import com.embanthe.dao.StatisticalDAO;
    import com.embanthe.model.Users;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.*;

    import java.io.IOException;
    import java.util.Map;

    @WebServlet("/admin")
    public class DashboardServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            HttpSession session = request.getSession(false);

            // not login back to login page
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            Users user = (Users) session.getAttribute("user");


            if ("ADMIN".equalsIgnoreCase(user.getRole())) {  // admin

                request.getRequestDispatcher("/page/admin/dashboard.jsp").forward(request, response);
            } else {
                // not admin back home
                response.sendRedirect(request.getContextPath() + "/home");
            }
        }

                @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            doGet(request, response);
        }
    }
