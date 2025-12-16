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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/admin/carditems/add")
public class CardItemAddServlet extends HttpServlet {
    private CardItemDAO cardItemDAO = new CardItemDAO();
    private ProductDAO productDAO = new ProductDAO();

    private static final int MAX_SERIAL_LENGTH = 64;
    private static final int MAX_CODE_LENGTH = 64;
    private static final int MIN_SERIAL_LENGTH = 3;
    private static final int MIN_CODE_LENGTH = 4;
    private static final int MAX_BATCH_SIZE = 500;
    private static final String SERIAL_PATTERN = "^[A-Za-z0-9_-]+$";
    private static final String CODE_PATTERN = "^[A-Za-z0-9_-]+$";

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
                productId = Integer.parseInt(productIdStr.trim());
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
            errorMessages.add("❌ Vui lòng chọn sản phẩm.");
        } else if (serialArr == null || codeArr == null || serialArr.length == 0) {
            errorMessages.add("❌ Danh sách thẻ trống. Vui lòng thêm ít nhất một thẻ.");
        } else if (serialArr.length != codeArr.length) {
            errorMessages.add("❌ Số lượng serial và mã thẻ không khớp.");
        } else {
            // Kiểm tra sản phẩm tồn tại và khả dụng
            Products product = productDAO.getById(productId);
            if (product == null) {
                errorMessages.add("❌ Sản phẩm không tồn tại.");
            } else {
                int n = serialArr.length;
                List<CardItems> toInsert = new ArrayList<>(n);
                List<String> serials = new ArrayList<>();
                List<String> codes = new ArrayList<>();

                // Track duplicate within request
                Set<String> reqSerialSet = new HashSet<>();
                Set<String> reqCodeSet = new HashSet<>();

                // Giới hạn batch
                if (n > MAX_BATCH_SIZE) {
                    warningMessages.add(String.format("⚠️ Chỉ xử lý tối đa %d hàng trong một lần. %d hàng vượt quá sẽ bị bỏ qua.",
                            MAX_BATCH_SIZE, n - MAX_BATCH_SIZE));
                    n = MAX_BATCH_SIZE;
                }

                for (int i = 0; i < n; ++i) {
                    String s = serialArr[i] == null ? "" : serialArr[i].trim();
                    String c = codeArr[i] == null ? "" : codeArr[i].trim();
                    String e = (expArr != null && i < expArr.length) ?
                            (expArr[i] == null ? "" : expArr[i].trim()) : "";

                    // Empty check
                    if (s.isEmpty() || c.isEmpty()) {
                        warningMessages.add(String.format("⚠️ Hàng %d bỏ qua: Serial hoặc mã thẻ trống.", i + 1));
                        continue;
                    }

                    // Min length checks
                    if (s.length() < MIN_SERIAL_LENGTH) {
                        warningMessages.add(String.format("⚠️ Hàng %d bỏ qua: Serial quá ngắn (%d ký tự). Tối thiểu: %d.",
                                i + 1, s.length(), MIN_SERIAL_LENGTH));
                        continue;
                    }
                    if (c.length() < MIN_CODE_LENGTH) {
                        warningMessages.add(String.format("⚠️ Hàng %d bỏ qua: Mã thẻ quá ngắn (%d ký tự). Tối thiểu: %d.",
                                i + 1, c.length(), MIN_CODE_LENGTH));
                        continue;
                    }

                    // Max length checks
                    if (s.length() > MAX_SERIAL_LENGTH) {
                        warningMessages.add(String.format("⚠️ Hàng %d bỏ qua: Serial quá dài (%d ký tự). Tối đa: %d.",
                                i + 1, s.length(), MAX_SERIAL_LENGTH));
                        continue;
                    }
                    if (c.length() > MAX_CODE_LENGTH) {
                        warningMessages.add(String.format("⚠️ Hàng %d bỏ qua: Mã thẻ quá dài (%d ký tự). Tối đa: %d.",
                                i + 1, c.length(), MAX_CODE_LENGTH));
                        continue;
                    }

                    // Pattern checks
                    if (!s.matches(SERIAL_PATTERN)) {
                        warningMessages.add(String.format("⚠️ Hàng %d bỏ qua: Serial chứa ký tự không hợp lệ. Chỉ cho phép chữ/số/-/_.", i + 1));
                        continue;
                    }
                    if (!c.matches(CODE_PATTERN)) {
                        warningMessages.add(String.format("⚠️ Hàng %d bỏ qua: Mã thẻ chứa ký tự không hợp lệ. Chỉ cho phép chữ/số/-/_.", i + 1));
                        continue;
                    }

                    // Duplicate in payload
                    if (!reqSerialSet.add(s)) {
                        warningMessages.add(String.format("⚠️ Trùng serial trong danh sách gửi (hàng %d): %s", i + 1, s));
                        continue;
                    }
                    if (!reqCodeSet.add(c)) {
                        warningMessages.add(String.format("⚠️ Trùng mã thẻ trong danh sách gửi (hàng %d): %s", i + 1, c));
                        continue;
                    }

                    CardItems item = new CardItems();
                    item.setProductId(productId);
                    item.setOrderId(null);
                    item.setSerialNumber(s);
                    item.setCardCode(c);

                    if (!e.isEmpty()) {
                        try {
                            Date exp = Date.valueOf(e);
                            Date today = new Date(System.currentTimeMillis());
                            if (exp.before(today)) {
                                warningMessages.add(String.format("⚠️ Hàng %d: Ngày hết hạn nằm trong quá khứ, bỏ qua giá trị này.", i + 1));
                            } else {
                                item.setExpirationDate(exp);
                            }
                        } catch (Exception ex) {
                            warningMessages.add(String.format("⚠️ Hàng %d: Định dạng ngày không hợp lệ, bỏ qua ngày hết hạn.", i + 1));
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
                    int skippedDbDup = 0;

                    for (CardItems ci : toInsert) {
                        if (existingSerials.contains(ci.getSerialNumber())) {
                            skippedDbDup++;
                            warningMessages.add(String.format("⚠️ Trùng serial trong cơ sở dữ liệu, bỏ qua: %s", ci.getSerialNumber()));
                            continue;
                        }
                        if (existingCodes.contains(ci.getCardCode())) {
                            skippedDbDup++;
                            warningMessages.add(String.format("⚠️ Trùng mã thẻ trong cơ sở dữ liệu, bỏ qua: %s", ci.getCardCode()));
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
                                errorMessages.add("❌ Cập nhật số lượng sản phẩm thất bại. Giao dịch đã rollback.");
                            } else {
                                con.commit();

                                String successMsg = String.format("✅ Thêm thành công %d thẻ!", finalInsert.size());
                                if (skippedDbDup > 0) {
                                    successMsg += String.format(" (%d thẻ trùng đã bị bỏ qua)", skippedDbDup);
                                }
                                successMessages.add(successMsg);

                                Products refreshed = productDAO.getById(productId);
                                if (refreshed != null) {
                                    successMessages.add(String.format("📦 Sản phẩm: %s - Tổng số thẻ mới: %d",
                                            refreshed.getProductName(), refreshed.getQuantity()));
                                }
                            }
                        } catch (Exception ex) {
                            if (con != null) {
                                try { con.rollback(); } catch (Exception ignore) {}
                            }

                            boolean isTruncation = false;
                            if (ex instanceof DataTruncation) {
                                isTruncation = true;
                            } else if (ex instanceof SQLException) {
                                String msg = ((SQLException) ex).getMessage();
                                if (msg != null && msg.toLowerCase().contains("data too long")) {
                                    isTruncation = true;
                                }
                            } else {
                                Throwable cause = ex.getCause();
                                if (cause instanceof SQLException) {
                                    String msg = ((SQLException) cause).getMessage();
                                    if (msg != null && msg.toLowerCase().contains("data too long")) {
                                        isTruncation = true;
                                    }
                                }
                            }

                            if (isTruncation) {
                                errorMessages.add(String.format("❌ Lỗi lưu dữ liệu: Giá trị quá dài so với cột DB. Vui lòng đảm bảo serial ≤ %d ký tự và mã thẻ ≤ %d ký tự.",
                                        MAX_SERIAL_LENGTH, MAX_CODE_LENGTH));
                            } else {
                                errorMessages.add("❌ Lỗi khi lưu thẻ. Vui lòng thử lại. Chi tiết: " + ex.getMessage());
                            }
                        } finally {
                            if (con != null) {
                                try {
                                    con.setAutoCommit(true);
                                    con.close();
                                } catch (Exception ignore) {}
                            }
                        }
                    } else {
                        warningMessages.add("⚠️ Không có thẻ hợp lệ để thêm. Tất cả thẻ đều trùng hoặc không hợp lệ.");
                    }
                } else {
                    warningMessages.add("⚠️ Không có thẻ hợp lệ để thêm. Vui lòng kiểm tra dữ liệu nhập.");
                }
            }
        }

        request.setAttribute("errorMessages", errorMessages);
        request.setAttribute("successMessages", successMessages);
        request.setAttribute("warningMessages", warningMessages);

        List<Products> products = productDAO.getAll();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/page/admin/carditemsadd.jsp").forward(request, response);
    }
}