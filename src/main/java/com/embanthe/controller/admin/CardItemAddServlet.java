package com.embanthe.controller.admin;

import com.embanthe.dao.CardItemDAO;
import com.embanthe.dao.ProductDAO;
import com.embanthe.model.CardItems;
import com.embanthe.model.Products;
import com.embanthe.util.DBContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet("/admin/carditems/add")
public class CardItemAddServlet extends HttpServlet {
    private CardItemDAO cardItemDAO = new CardItemDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Products> products = productDAO.getAll();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/page/admin/carditemsadd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String productIdStr = request.getParameter("productId");
        Integer productId = null;
        try {
            if (productIdStr != null && !productIdStr.isEmpty()) {
                productId = Integer.parseInt(productIdStr);
            }
        } catch (Exception ignored) {}

        String[] serialArr = request.getParameterValues("serialNumber[]");
        if (serialArr == null) serialArr = request.getParameterValues("serialNumber");

        String[] codeArr = request.getParameterValues("cardCode[]");
        if (codeArr == null) codeArr = request.getParameterValues("cardCode");

        String[] expArr = request.getParameterValues("expirationDate[]");
        if (expArr == null) expArr = request.getParameterValues("expirationDate");

        List<String> successMessages = new ArrayList<>();
        List<String> warningMessages = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        if (productId == null) {
            errorMessages.add("❌ Please select a product.");
        } else if (serialArr == null || codeArr == null || serialArr.length == 0) {
            errorMessages.add("❌ Card list is empty. Please add at least one card.");
        } else if (serialArr.length != codeArr.length) {
            errorMessages.add("❌ Number of serial numbers and card codes do not match.");
        } else {
            int n = serialArr.length;
            List<CardItems> toInsert = new ArrayList<>(n);
            List<String> serials = new ArrayList<>();
            List<String> codes = new ArrayList<>();

            for (int i = 0; i < n; ++i) {
                String s = serialArr[i] == null ? "" : serialArr[i].trim();
                String c = codeArr[i] == null ? "" : codeArr[i].trim();
                String e = (expArr != null && i < expArr.length) ?
                        (expArr[i] == null ? "" : expArr[i].trim()) : "";

                if (s.isEmpty() || c.isEmpty()) {
                    warningMessages.add(String.format(
                            "⚠️ Row %d skipped: Serial number or card code is empty.", i + 1));
                    continue;
                }

                CardItems item = new CardItems();
                item.setProductId(productId);
                item.setOrderId(null);
                item.setSerialNumber(s);
                item.setCardCode(c);

                if (!e.isEmpty()) {
                    try {
                        item.setExpirationDate(Date.valueOf(e));
                    } catch (Exception ex) {
                        warningMessages.add(String.format(
                                "⚠️ Row %d: Invalid date format, expiration date ignored.", i + 1));
                    }
                }

                item.setStatus("AVAILABLE");
                item.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                toInsert.add(item);
                serials.add(s);
                codes.add(c);
            }

            if (!toInsert.isEmpty()) {
                Set<String> existingSerials = cardItemDAO.findExistingSerials(serials);
                Set<String> existingCodes = cardItemDAO.findExistingCodes(codes);

                List<CardItems> finalInsert = new ArrayList<>();
                int skipped = 0;

                for (CardItems ci : toInsert) {
                    if (existingSerials.contains(ci.getSerialNumber())) {
                        skipped++;
                        warningMessages.add(String.format(
                                "⚠️ Duplicate serial skipped: %s", ci.getSerialNumber()));
                        continue;
                    }
                    if (existingCodes.contains(ci.getCardCode())) {
                        skipped++;
                        warningMessages.add(String.format(
                                "⚠️ Duplicate card code skipped: %s", ci.getCardCode()));
                        continue;
                    }
                    finalInsert.add(ci);
                }

                if (!finalInsert.isEmpty()) {
                    Connection con = null;
                    try {
                        con = DBContext.getInstance().getConnection();
                        con.setAutoCommit(false);

                        cardItemDAO.insertBatch(con, finalInsert);

                        boolean qtyOk = productDAO.adjustQuantityWithCheck(
                                con, productId, finalInsert.size());

                        if (!qtyOk) {
                            con.rollback();
                            errorMessages.add(
                                    "❌ Failed to update product quantity. Transaction rolled back.");
                        } else {
                            con.commit();

                            String successMsg = String.format(
                                    "✅ Successfully added %d card(s)!", finalInsert.size());

                            if (skipped > 0) {
                                successMsg += String.format(" (%d duplicate(s) skipped)", skipped);
                            }

                            successMessages.add(successMsg);

                            // Get product name for better message
                            Products product = productDAO.getById(productId);
                            if (product != null) {
                                successMessages.add(String.format(
                                        "📦 Product: <strong>%s</strong> - New total: <strong>%d cards</strong>",
                                        product.getProductName(),
                                        product.getQuantity()
                                ));
                            }
                        }
                    } catch (Exception ex) {
                        if (con != null) {
                            try {
                                con.rollback();
                            } catch (Exception ignore) {}
                        }
                        ex.printStackTrace();
                        errorMessages.add(
                                "❌ Error saving cards. Please try again. Details: " + ex.getMessage());
                    } finally {
                        if (con != null) {
                            try {
                                con.setAutoCommit(true);
                                con.close();
                            } catch (Exception ignore) {}
                        }
                    }
                } else {
                    warningMessages.add(
                            "⚠️ No valid cards to add. All cards are duplicates or invalid.");
                }
            } else {
                warningMessages.add("⚠️ No valid cards to add. Please check your input.");
            }
        }

        // Build final message
        StringBuilder finalMessage = new StringBuilder();

        if (!errorMessages.isEmpty()) {
            finalMessage.append("<div style='color: #991b1b; margin-bottom: 10px;'>");
            finalMessage.append(String.join("<br/>", errorMessages));
            finalMessage.append("</div>");
        }

        if (!successMessages.isEmpty()) {
            finalMessage.append("<div style='color: #065f46; margin-bottom: 10px;'>");
            finalMessage.append(String.join("<br/>", successMessages));
            finalMessage.append("</div>");
        }

        if (!warningMessages.isEmpty()) {
            finalMessage.append("<div style='color: #92400e; margin-bottom: 10px;'>");
            finalMessage.append(String.join("<br/>", warningMessages));
            finalMessage.append("</div>");
        }

        // Reload products for the form
        List<Products> products = productDAO.getAll();
        request.setAttribute("products", products);
        request.setAttribute("message", finalMessage.toString());
        request.getRequestDispatcher("/page/admin/carditemsadd.jsp").forward(request, response);
    }
}