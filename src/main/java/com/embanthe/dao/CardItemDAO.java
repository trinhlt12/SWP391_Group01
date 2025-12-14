package com.embanthe.dao;

import com.embanthe.model.CardItems;
import com.embanthe.util.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardItemDAO {

    public List<CardItems> getPagedList(String searchSerial, String searchCode, Integer productId, String status, int offset, int pageSize) {
        List<CardItems> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT i.card_item_id, i.serial_number, i.card_code, i.expiration_date, i.status, i.created_at, " +
                        "p.product_name, p.price " +
                        "FROM card_items i JOIN products p ON i.product_id = p.product_id WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();
        if (searchSerial != null && !searchSerial.isEmpty()) {
            sql.append("AND i.serial_number LIKE ? ");
            params.add("%" + searchSerial.trim() + "%");
        }
        if (searchCode != null && !searchCode.isEmpty()) {
            sql.append("AND i.card_code LIKE ? ");
            params.add("%" + searchCode.trim() + "%");
        }
        if (productId != null && productId > 0) {
            sql.append("AND i.product_id = ? ");
            params.add(productId);
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND i.status = ? ");
            params.add(status);
        }
        sql.append("ORDER BY i.card_item_id DESC LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(offset);

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); ++i)
                ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CardItems c = new CardItems();
                    c.setCardItemId(rs.getInt("card_item_id"));
                    c.setSerialNumber(rs.getString("serial_number"));
                    c.setCardCode(rs.getString("card_code"));
                    c.setExpirationDate(rs.getDate("expiration_date"));
                    c.setStatus(rs.getString("status"));
                    c.setCreatedAt(rs.getTimestamp("created_at"));
                    c.setProductName(rs.getString("product_name"));
                    c.setPrice(rs.getInt("price"));
                    list.add(c);
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return list;
    }

    public int count(String searchSerial, String searchCode, Integer productId, String status) {
        StringBuilder sql = new StringBuilder(
                "SELECT count(*) FROM card_items i WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();
        if (searchSerial != null && !searchSerial.isEmpty()) {
            sql.append("AND i.serial_number LIKE ? ");
            params.add("%" + searchSerial.trim() + "%");
        }
        if (searchCode != null && !searchCode.isEmpty()) {
            sql.append("AND i.card_code LIKE ? ");
            params.add("%" + searchCode.trim() + "%");
        }
        if (productId != null && productId > 0) {
            sql.append("AND i.product_id = ? ");
            params.add(productId);
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND i.status = ? ");
            params.add(status);
        }
        int cnt = 0;
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); ++i)
                ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) cnt = rs.getInt(1);
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return cnt;
    }
    public int addCardItems(List<CardItems> cards) {
        String sql = "INSERT INTO card_items (product_id, serial_number, card_code, expiration_date, status, created_at) VALUES (?, ?, ?, ?, ?, NOW())";
        int count = 0;
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (CardItems card : cards) {
                ps.setInt(1, card.getProductId());
                ps.setString(2, card.getSerialNumber());
                ps.setString(3, card.getCardCode());
                ps.setDate(4, card.getExpirationDate());
                ps.setString(5, card.getStatus() == null ? "AVAILABLE" : card.getStatus());
                ps.addBatch();
            }
            int[] rs = ps.executeBatch();
            // Ghi đè: luôn trả về cards.size(), giả sử không lỗi; nếu muốn bắt lỗi từng dòng, kiểm tra và xử lý riêng
            count = cards.size();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }
}