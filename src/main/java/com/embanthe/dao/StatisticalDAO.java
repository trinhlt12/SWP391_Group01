package com.embanthe.dao;

import com.embanthe.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticalDAO {

    // ==========================================
    // INNER CLASS: DTO (Dùng để hứng dữ liệu)
    // ==========================================
    public static class AdminActivity {
        private String userName;
        private String action;
        private String time;
        private String type; // Thêm type để dễ xử lý màu sắc nếu cần (VD: DEPOSIT màu xanh, PURCHASE màu đỏ)

        public AdminActivity() {}

        public AdminActivity(String userName, String action, String time, String type) {
            this.userName = userName;
            this.action = action;
            this.time = time;
            this.type = type;
        }

        // Getters and Setters
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    // ==========================================
    // 1. THỐNG KÊ TỔNG QUAN (DASHBOARD CARDS)
    // ==========================================

    public int countTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users WHERE status != 'BANNED'"; // Có thể lọc user bị ban nếu muốn
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public double getTotalRevenue() {

        String sql = "SELECT SUM(amount) FROM transactions WHERE status = 'SUCCESS' AND type = 'DEPOSIT'";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0.0;
    }

    public int countTransactionsToday() {
        String sql = "SELECT COUNT(*) FROM transactions WHERE DATE(created_at) = CURDATE()";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public Map<String, Double> getRevenueLast7Days() {
        Map<String, Double> map = new LinkedHashMap<>();

        // Query này lấy 7 ngày gần nhất, group lại và sắp xếp từ cũ đến mới
        String sql = "SELECT DATE_FORMAT(created_at, '%d/%m') as date_label, SUM(amount) as total " +
                "FROM transactions " +
                "WHERE status = 'SUCCESS' " +
                "AND type = 'DEPOSIT' " + // Chỉ tính tiền nạp
                "AND created_at >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +
                "GROUP BY DATE_FORMAT(created_at, '%d/%m'), DATE(created_at) " +
                "ORDER BY DATE(created_at) ASC";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("date_label"), rs.getDouble("total"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return map;
    }

    public Map<String, Integer> getOrdersLast7Days() {
        Map<String, Integer> map = new LinkedHashMap<>();

        String sql = "SELECT DATE_FORMAT(created_at, '%d/%m') as date_label, COUNT(*) as total " +
                "FROM orders " + // Nên đếm từ bảng ORDERS nếu muốn đếm số đơn mua thẻ
                "WHERE status = 'COMPLETED' " +
                "AND created_at >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +
                "GROUP BY DATE_FORMAT(created_at, '%d/%m'), DATE(created_at) " +
                "ORDER BY DATE(created_at) ASC";

        // Nếu bạn muốn đếm Transaction thay vì Order thì đổi bảng 'orders' thành 'transactions'

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("date_label"), rs.getInt("total"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return map;
    }

    public List<AdminActivity> getRecentActivities(String keyword, String type, String fromDate, String toDate, int page, int pageSize) {
        List<AdminActivity> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT u.username, t.type, t.amount, t.created_at " +
                        "FROM transactions t JOIN users u ON t.user_id = u.user_id WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        // --- Logic lọc giống hệt hàm count ---
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND u.username LIKE ? ");
            params.add("%" + keyword + "%");
        }
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND t.type = ? ");
            params.add(type);
        }
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            sql.append(" AND t.created_at >= ? ");
            params.add(fromDate + " 00:00:00");
        }
        if (toDate != null && !toDate.trim().isEmpty()) {
            sql.append(" AND t.created_at <= ? ");
            params.add(toDate + " 23:59:59");
        }
        // -------------------------------------

        // Thêm sắp xếp và phân trang
        sql.append(" ORDER BY t.created_at DESC LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tType = rs.getString("type");
                    double amount = rs.getDouble("amount");
                    String userName = rs.getString("username");
                    Timestamp ts = rs.getTimestamp("created_at");

                    String actionDesc = "";
                    switch (tType) {
                        case "DEPOSIT": actionDesc = "Nạp tiền: +" + String.format("%,.0f", amount); break;
                        case "PURCHASE": actionDesc = "Mua thẻ: -" + String.format("%,.0f", Math.abs(amount)); break; // Sửa hiển thị trừ tiền
                        case "REFUND": actionDesc = "Hoàn tiền: +" + String.format("%,.0f", amount); break;
                        default: actionDesc = tType + ": " + amount;
                    }

                    list.add(new AdminActivity(userName, actionDesc, sdf.format(ts), tType));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    public int getTotalActivities(String keyword, String type, String fromDate, String toDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM transactions t JOIN users u ON t.user_id = u.user_id WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        // 1. Lọc theo tên username
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND u.username LIKE ? ");
            params.add("%" + keyword + "%");
        }

        // 2. Lọc theo loại (DEPOSIT, PURCHASE...)
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND t.type = ? ");
            params.add(type);
        }

        // 3. Lọc từ ngày
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            sql.append(" AND t.created_at >= ? ");
            params.add(fromDate + " 00:00:00"); // Bắt đầu ngày
        }

        // 4. Lọc đến ngày
        if (toDate != null && !toDate.trim().isEmpty()) {
            sql.append(" AND t.created_at <= ? ");
            params.add(toDate + " 23:59:59"); // Hết ngày
        }

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Gán tham số vào dấu ?
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
}