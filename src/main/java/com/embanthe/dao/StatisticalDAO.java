package com.embanthe.dao;

import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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
    public Map<String, Integer> getOrdersLast7Days() {
        Map<String, Integer> map = new LinkedHashMap<>();

        String sql = "SELECT DATE_FORMAT(created_at, '%d/%m') as date_label, COUNT(*) as total " +
                "FROM Transactions " +
                "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                "GROUP BY DATE_FORMAT(created_at, '%d/%m') " +
                "ORDER BY MIN(created_at) ASC";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("date_label"), rs.getInt("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<Map<String, String>> getRecentActivities() {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "SELECT u.username, t.type, t.created_at " +
                "FROM Transactions t " +
                "JOIN Users u ON t.user_id = u.user_id " +
                "ORDER BY t.created_at DESC LIMIT 11";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> act = new HashMap<>();
                act.put("userName", rs.getString("username"));
                act.put("action", "Thực hiện giao dịch: " + rs.getString("type"));
                act.put("time", rs.getTimestamp("created_at").toString());
                list.add(act);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    public class AdminActivity {
        public String userName;
        public String action;
        public String time;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}
