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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Products> products = productDAO.getAll();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/page/admin/carditemsadd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String productIdStr = request.getParameter("productId");
        Integer productId = null;
        try {
            if (productIdStr != null && !productIdStr.isEmpty()) productId = Integer.parseInt(productIdStr);
        } catch (Exception ignored) {}

        String[] serialArr = request.getParameterValues("serialNumber[]");
        if (serialArr == null) serialArr = request.getParameterValues("serialNumber");
        String[] codeArr = request.getParameterValues("cardCode[]");
        if (codeArr == null) codeArr = request.getParameterValues("cardCode");
        String[] expArr = request.getParameterValues("expirationDate[]");
        if (expArr == null) expArr = request.getParameterValues("expirationDate");

        List<String> messages = new ArrayList<>();

        if (productId == null) {
            messages.add("Vui lòng chọn sản phẩm.");
        } else if (serialArr == null || codeArr == null || serialArr.length == 0) {
            messages.add("Danh sách thẻ rỗng.");
        } else if (serialArr.length != codeArr.length) {
            messages.add("Số lượng serial và mã nạp không khớp.");
        } else {
            int n = serialArr.length;
            List<CardItems> toInsert = new ArrayList<>(n);
            List<String> serials = new ArrayList<>();
            List<String> codes = new ArrayList<>();

            for (int i = 0; i < n; ++i) {
                String s = serialArr[i] == null ? "" : serialArr[i].trim();
                String c = codeArr[i] == null ? "" : codeArr[i].trim();
                String e = (expArr != null && i < expArr.length) ? (expArr[i] == null ? "" : expArr[i].trim()) : "";

                if (s.isEmpty() || c.isEmpty()) {
                    messages.add(String.format("Dòng %d bỏ qua: serial hoặc mã nạp rỗng.", i + 1));
                    continue;
                }

                CardItems item = new CardItems();
                item.setProductId(productId);
                item.setOrderId(null); // chưa bán
                item.setSerialNumber(s);
                item.setCardCode(c);
                if (!e.isEmpty()) {
                    try {
                        item.setExpirationDate(Date.valueOf(e)); // format yyyy-MM-dd
                    } catch (Exception ex) {
                        // invalid date -> keep null and add message
                        messages.add(String.format("Dòng %d: định dạng ngày không hợp lệ, bỏ qua ngày.", i + 1));
                    }
                }
                item.setStatus("AVAILABLE");
                item.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                toInsert.add(item);
                serials.add(s);
                codes.add(c);
            }

            if (!toInsert.isEmpty()) {
                // kiểm tra serial/cardCode đã tồn tại trong DB
                Set<String> existingSerials = cardItemDAO.findExistingSerials(serials);
                Set<String> existingCodes = cardItemDAO.findExistingCodes(codes);

                List<CardItems> finalInsert = new ArrayList<>();
                int skipped = 0;
                for (CardItems ci : toInsert) {
                    if (existingSerials.contains(ci.getSerialNumber())) {
                        skipped++;
                        messages.add("Bỏ qua serial đã tồn tại: " + ci.getSerialNumber());
                        continue;
                    }
                    if (existingCodes.contains(ci.getCardCode())) {
                        skipped++;
                        messages.add("Bỏ qua mã nạp đã tồn tại: " + ci.getCardCode());
                        continue;
                    }
                    finalInsert.add(ci);
                }

                if (!finalInsert.isEmpty()) {
                    Connection con = null;
                    try {
                        con = DBContext.getInstance().getConnection();
                        con.setAutoCommit(false);

                        // insert batch using the connection
                        cardItemDAO.insertBatch(con, finalInsert);

                        // update product quantity (increase by number of inserted cards)
                        boolean qtyOk = productDAO.adjustQuantityWithCheck(con, productId, finalInsert.size());
                        if (!qtyOk) {
                            con.rollback();
                            messages.add("Không thể cập nhật số lượng sản phẩm (quantity). Thao tác đã bị huỷ.");
                        } else {
                            con.commit();
                            messages.add("Thêm thành công " + finalInsert.size() + " thẻ. (Bỏ qua " + skipped + " thẻ)");
                        }
                    } catch (Exception ex) {
                        if (con != null) {
                            try { con.rollback(); } catch (Exception ignore) {}
                        }
                        ex.printStackTrace();
                        messages.add("Lỗi khi lưu thẻ. Vui lòng thử lại.");
                    } finally {
                        if (con != null) {
                            try { con.setAutoCommit(true); con.close(); } catch (Exception ignore) {}
                        }
                    }
                } else {
                    messages.add("Không có thẻ hợp lệ để thêm. (Tất cả bị trùng hoặc không hợp lệ)");
                }
            } else {
                messages.add("Không có thẻ hợp lệ để thêm.");
            }
        }

        // reload products for the form and show messages
        List<Products> products = productDAO.getAll();
        request.setAttribute("products", products);
        request.setAttribute("message", String.join("<br/>", messages));
        request.getRequestDispatcher("/page/admin/carditemsadd.jsp").forward(request, response);
    }
}