package com.embanthe.dao;

import com.embanthe.model.CardItems;
import com.embanthe.util.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardItemDAO {

    public List<CardItems> getPagedList(String searchSerial, String searchCode, Integer productId, String status, int offset, int pageSize) {
        List<CardItems> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT i.card_item_id, i.product_id, i.order_id, i.serial_number, i.card_code, i.expiration_date, i.status, i.created_at " +
                        "FROM card_items i WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();

        if (searchSerial != null && !searchSerial.trim().isEmpty()) {
            sql.append("AND i.serial_number LIKE ? ");
            params.add("%" + searchSerial.trim() + "%");
        }
        if (searchCode != null && !searchCode.trim().isEmpty()) {
            sql.append("AND i.card_code LIKE ? ");
            params.add("%" + searchCode.trim() + "%");
        }
        if (productId != null && productId > 0) {
            sql.append("AND i.product_id = ? ");
            params.add(productId);
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append("AND i.status = ? ");
            params.add(status.trim());
        }

        sql.append("ORDER BY i.card_item_id DESC LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(offset);

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); ++i) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CardItems c = new CardItems();
                    c.setCardItemId(rs.getInt("card_item_id"));
                    c.setProductId(rs.getInt("product_id"));
                    int oid = rs.getInt("order_id");
                    if (rs.wasNull()) c.setOrderId(null);
                    else c.setOrderId(oid);
                    c.setSerialNumber(rs.getString("serial_number"));
                    c.setCardCode(rs.getString("card_code"));
                    c.setExpirationDate(rs.getDate("expiration_date"));
                    c.setStatus(rs.getString("status"));
                    c.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(c);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int count(String searchSerial, String searchCode, Integer productId, String status) {
        StringBuilder sql = new StringBuilder("SELECT count(*) FROM card_items i WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (searchSerial != null && !searchSerial.trim().isEmpty()) {
            sql.append("AND i.serial_number LIKE ? ");
            params.add("%" + searchSerial.trim() + "%");
        }
        if (searchCode != null && !searchCode.trim().isEmpty()) {
            sql.append("AND i.card_code LIKE ? ");
            params.add("%" + searchCode.trim() + "%");
        }
        if (productId != null && productId > 0) {
            sql.append("AND i.product_id = ? ");
            params.add(productId);
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append("AND i.status = ? ");
            params.add(status.trim());
        }

        int cnt = 0;
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); ++i) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) cnt = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cnt;
    }

    /**
     * Trả về tập các serial đã tồn tại trong DB (giao với input)
     */
    public Set<String> findExistingSerials(List<String> serials) {
        return findExistingSingleColumn("serial_number", serials);
    }

    /**
     * Trả về tập các card_code đã tồn tại trong DB (giao với input)
     */
    public Set<String> findExistingCodes(List<String> codes) {
        return findExistingSingleColumn("card_code", codes);
    }

    private Set<String> findExistingSingleColumn(String column, List<String> values) {
        Set<String> result = new HashSet<>();
        if (values == null || values.isEmpty()) return result;

        // clean values
        List<String> clean = new ArrayList<>();
        for (String v : values) {
            if (v != null) {
                String t = v.trim();
                if (!t.isEmpty()) clean.add(t);
            }
        }
        if (clean.isEmpty()) return result;

        // build SQL with IN (?, ?, ...)
        StringBuilder sb = new StringBuilder("SELECT " + column + " FROM card_items WHERE " + column + " IN (");
        for (int i = 0; i < clean.size(); ++i) {
            if (i > 0) sb.append(",");
            sb.append("?");
        }
        sb.append(")");

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sb.toString())) {

            for (int i = 0; i < clean.size(); ++i) {
                ps.setString(i + 1, clean.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Insert many CardItems in a single transaction using batch.
     * Caller manages the transaction (commit/rollback).
     */
    public void insertBatch(Connection con, List<CardItems> items) throws SQLException {
        if (items == null || items.isEmpty()) return;

        String sql = "INSERT INTO card_items (product_id, order_id, serial_number, card_code, expiration_date, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (CardItems c : items) {
                ps.setInt(1, c.getProductId());
                if (c.getOrderId() == null) {
                    ps.setNull(2, Types.INTEGER);
                } else {
                    ps.setInt(2, c.getOrderId());
                }
                ps.setString(3, c.getSerialNumber());
                ps.setString(4, c.getCardCode());
                if (c.getExpirationDate() == null) {
                    ps.setNull(5, Types.DATE);
                } else {
                    ps.setDate(5, c.getExpirationDate());
                }
                ps.setString(6, c.getStatus());
                if (c.getCreatedAt() == null) {
                    ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                } else {
                    ps.setTimestamp(7, c.getCreatedAt());
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    /**
     * Backward-compatible convenience method: uses its own connection and transaction.
     */
    public boolean insertBatch(List<CardItems> items) {
        if (items == null || items.isEmpty()) return true;

        String sql = "INSERT INTO card_items (product_id, order_id, serial_number, card_code, expiration_date, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBContext.getInstance().getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);

            for (CardItems c : items) {
                ps.setInt(1, c.getProductId());
                if (c.getOrderId() == null) {
                    ps.setNull(2, Types.INTEGER);
                } else {
                    ps.setInt(2, c.getOrderId());
                }
                ps.setString(3, c.getSerialNumber());
                ps.setString(4, c.getCardCode());
                if (c.getExpirationDate() == null) {
                    ps.setNull(5, Types.DATE);
                } else {
                    ps.setDate(5, c.getExpirationDate());
                }
                ps.setString(6, c.getStatus());
                if (c.getCreatedAt() == null) {
                    ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                } else {
                    ps.setTimestamp(7, c.getCreatedAt());
                }
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ignore) {
                }
            }
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ignore) {
            }
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }

    // Thêm vào CardItemDAO.java

    /**
     * Lấy danh sách thẻ khả dụng để bán và KHÓA dòng dữ liệu đó lại (Locking).
     *
     * @param conn      Kết nối chung của Transaction
     * @param productId ID sản phẩm
     * @param quantity  Số lượng cần mua
     */
    public List<CardItems> getAvailableCardsForUpdate(Connection conn, int productId, int quantity) throws SQLException {
        List<CardItems> list = new ArrayList<>();
        // LIMIT ? : Chỉ lấy đúng số lượng khách mua
        // FOR UPDATE : Khóa dòng, chặn các transaction khác đụng vào các thẻ này
        String sql = "SELECT card_item_id, serial_number, card_code, expiration_date " +
                "FROM card_items " +
                "WHERE product_id = ? AND status = 'AVAILABLE' " +
                "LIMIT ? FOR UPDATE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setInt(2, quantity);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CardItems c = new CardItems();
                    c.setCardItemId(rs.getInt("card_item_id"));
                    c.setSerialNumber(rs.getString("serial_number"));
                    c.setCardCode(rs.getString("card_code"));
                    c.setExpirationDate(rs.getDate("expiration_date"));
                    list.add(c);
                }
            }
        }
        return list;
    }

    /**
     * Cập nhật trạng thái thẻ sang SOLD và gán Order ID
     */
    public void updateCardStatusToSold(Connection conn, int cardItemId, int orderId) throws SQLException {
        String sql = "UPDATE card_items SET status = 'SOLD', order_id = ? WHERE card_item_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, cardItemId);
            ps.executeUpdate();
        }
    }

    public List<CardItems> getPurchasedCardsByUserId(int userId) {
        List<CardItems> list = new ArrayList<>();
        // Join 3 bảng để lấy đầy đủ thông tin
        String sql = "SELECT c.card_item_id, c.serial_number, c.card_code, c.created_at, c.expiration_date, " +
                "p.product_name, p.price, c.order_id " +
                "FROM card_items c " +
                "JOIN orders o ON c.order_id = o.order_id " +
                "JOIN products p ON c.product_id = p.product_id " +
                "WHERE o.user_id = ? AND c.status = 'SOLD' " +
                "ORDER BY c.created_at DESC";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CardItems c = new CardItems();
                    c.setCardItemId(rs.getInt("card_item_id"));
                    c.setSerialNumber(rs.getString("serial_number"));
                    c.setCardCode(rs.getString("card_code"));
                    c.setCreatedAt(rs.getTimestamp("created_at"));
                    c.setExpirationDate(rs.getDate("expiration_date"));
                    c.setOrderId(rs.getInt("order_id"));

                    // Set các trường bổ sung
                    c.setProductName(rs.getString("product_name"));
                    c.setPrice(rs.getDouble("price"));

                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<CardItems> getPurchasedCardsPaging(int userId, String keyword, int pageIndex, int pageSize) {
        List<CardItems> list = new ArrayList<>();
        int offset = (pageIndex - 1) * pageSize;

        StringBuilder sql = new StringBuilder(
                // SỬA: Lấy product_name và unit_price từ bảng ORDERS (o)
                "SELECT c.card_item_id, c.serial_number, c.card_code, o.created_at, c.expiration_date, " +
                        "o.product_name, o.unit_price, c.order_id " +
                        "FROM card_items c " +
                        "JOIN orders o ON c.order_id = o.order_id " +
                        "WHERE o.user_id = ? AND c.status = 'SOLD' ");

        if (keyword != null && !keyword.trim().isEmpty()) {
            // SỬA: Tìm kiếm theo o.product_name
            sql.append("AND (o.product_name LIKE ? OR c.serial_number LIKE ? OR c.card_code LIKE ?) ");
        }

        sql.append("ORDER BY o.created_at DESC LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            ps.setInt(paramIndex++, userId);

            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchPattern = "%" + keyword.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }

            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex++, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CardItems c = new CardItems();
                    c.setCardItemId(rs.getInt("card_item_id"));
                    c.setSerialNumber(rs.getString("serial_number"));
                    c.setCardCode(rs.getString("card_code"));
                    c.setCreatedAt(rs.getTimestamp("created_at"));
                    c.setExpirationDate(rs.getDate("expiration_date"));
                    c.setOrderId(rs.getInt("order_id"));

                    c.setProductName(rs.getString("product_name"));
                    c.setPrice(rs.getDouble("unit_price")); // Lưu ý tên cột trong DB là unit_price

                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countPurchasedCards(int userId, String keyword) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) " +
                        "FROM card_items c " +
                        "JOIN orders o ON c.order_id = o.order_id " +
                        "WHERE o.user_id = ? AND c.status = 'SOLD' ");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (o.product_name LIKE ? OR c.serial_number LIKE ? OR c.card_code LIKE ?) ");
        }

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            ps.setInt(paramIndex++, userId);

            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchPattern = "%" + keyword.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}