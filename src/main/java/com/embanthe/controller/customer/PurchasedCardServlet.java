package com.embanthe.controller.customer;

import com.embanthe.dao.CardItemDAO;
import com.embanthe.model.CardItems;
import com.embanthe.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/purchased-cards")
public class PurchasedCardServlet extends HttpServlet {

    private final CardItemDAO cardItemDAO = new CardItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String keyword = req.getParameter("search");
        String pageStr = req.getParameter("page");

        int page = 1;
        int pageSize = 10;

        try {
            if (pageStr != null && !pageStr.isEmpty()) {
                page = Integer.parseInt(pageStr);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        List<CardItems> list = cardItemDAO.getPurchasedCardsPaging(user.getUserId(), keyword, page, pageSize);
        int totalRecords = cardItemDAO.countPurchasedCards(user.getUserId(), keyword);

        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        req.setAttribute("purchasedList", list);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("searchKeyword", keyword);

        req.getRequestDispatcher("/page/customer/purchased-cards.jsp").forward(req, resp);
    }
}