package com.embanthe.dao;

import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatisticalDAO {
    public int countTotalUsers() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // 2. Tính tổng doanh thu (Giả sử bảng Transactions có cột amount)
    public double getTotalRevenue() {
        String sql = "SELECT SUM(amount) FROM Transactions WHERE status = 'SUCCESS'";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // 3. Đếm số giao dịch hôm nay
    public int countTransactionsToday() {
        // CURDATE() lấy ngày hiện tại của SQL
        String sql = "SELECT COUNT(*) FROM Transactions WHERE DATE(created_at) = CURDATE()";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // 4. Lấy dữ liệu cho biểu đồ (Doanh thu 7 ngày gần nhất)
    // Trả về Map: Key là Ngày (String), Value là Tiền (Double)
    public Map<String, Double> getRevenueLast7Days() {
        Map<String, Double> map = new LinkedHashMap<>(); // Dùng LinkedHashMap để giữ thứ tự
        String sql = "SELECT DATE(created_at) as date, SUM(amount) as total " +
                "FROM Transactions " +
                "WHERE status = 'SUCCESS' " +
                "GROUP BY DATE(created_at) " +
                "ORDER BY date DESC LIMIT 7";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("date"), rs.getDouble("total"));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return map;
    }
}
