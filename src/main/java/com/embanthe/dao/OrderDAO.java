package com.embanthe.dao;

import java.sql.*;

public class OrderDAO {
    public int createOrder(Connection conn, int userId, int productId, int quantity, double totalAmount) throws SQLException {
        String sql = "INSERT INTO orders (user_id, product_id, quantity, total_amount, status, created_at) VALUES (?, ?, ?, ?, 'COMPLETED', NOW())";
        // Statement.RETURN_GENERATED_KEYS: Để lấy lại Order ID vừa sinh ra
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, totalAmount);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Trả về order_id
                }
            }
        }
        return 0;
    }
}
