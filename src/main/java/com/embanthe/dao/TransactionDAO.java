package com.embanthe.dao;

import com.embanthe.model.Transactions;
import com.embanthe.util.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public int createDepositTransaction(Transactions trans) {
        String sql = "INSERT INTO transactions (user_id, amount, type, status, message, created_at) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, (int) trans.getUserId());
            ps.setDouble(2, trans.getAmount());
            ps.setString(3, trans.getType());   // 'DEPOSIT'
            ps.setString(4, trans.getStatus()); // 'PENDING'
            ps.setString(5, trans.getMessage());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting transaction: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }


    public void createTransaction(Connection conn, int userId, int orderId, double amount, String message) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, order_id, amount, type, status, message, created_at) VALUES (?, ?, ?, 'PURCHASE', 'SUCCESS', ?, NOW())";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, orderId);
            ps.setDouble(3, -amount); // Lưu số âm: ví dụ -50000
            ps.setString(4, message);
            ps.executeUpdate();
        }
    }

    public Transactions getTransactionById(int id) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Transactions trans = new Transactions();
                    trans.setTransactionId(rs.getInt("transaction_id"));
                    trans.setUserId(rs.getInt("user_id"));
                    trans.setAmount(rs.getDouble("amount"));
                    trans.setType(rs.getString("type"));
                    trans.setStatus(rs.getString("status"));
                    trans.setMessage(rs.getString("message"));
                    return trans;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Transactions getTransactionByOrderId(int orderId) {
        String sql = "SELECT * FROM transactions WHERE order_id = ? AND type = 'PURCHASE'";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Transactions t = new Transactions();
                    t.setTransactionId(rs.getInt("transaction_id"));
                    t.setUserId(rs.getInt("user_id"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setType(rs.getString("type"));
                    t.setStatus(rs.getString("status"));
                    t.setMessage(rs.getString("message"));
                    t.setCreatedAt(rs.getTimestamp("created_at"));
                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updateTransactionStatus(int transactionId, String status, String message) {
        String sql = "UPDATE transactions SET status = ?, message = ? WHERE transaction_id = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, message);
            ps.setInt(3, transactionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transactions> getRecentTransactions(int userId, int pageIndex, int pageSize) {
        return getRecentTransactions(userId, "ALL", pageIndex, pageSize);
    }

    public List<Transactions> getRecentTransactions(int userId, String filterValue, int pageIndex, int pageSize) {
        List<Transactions> list = new ArrayList<>();
        int offset = (pageIndex - 1) * pageSize;

        StringBuilder sql = new StringBuilder("SELECT * FROM transactions WHERE user_id = ?");

        boolean hasFilter = (filterValue != null && !filterValue.trim().isEmpty() && !filterValue.equals("ALL"));

        if (hasFilter) {
            if (filterValue.equals("DEPOSIT") || filterValue.equals("PURCHASE")) {
                sql.append(" AND type = ?");   // Lọc theo cột TYPE
            } else {
                sql.append(" AND status = ?"); // Lọc theo cột STATUS
            }
        }

        sql.append(" ORDER BY created_at DESC LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            ps.setInt(paramIndex++, userId);

            if (hasFilter) {
                ps.setString(paramIndex++, filterValue);
            }

            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex++, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transactions t = new Transactions();
                    t.setTransactionId(rs.getInt("transaction_id"));
                    t.setUserId(rs.getInt("user_id"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setType(rs.getString("type"));
                    t.setStatus(rs.getString("status"));
                    t.setMessage(rs.getString("message"));
                    t.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTransactionsByUserId(int userId) {
        return countTransactionsByUserId(userId, "ALL");
    }

    public int countTransactionsByUserId(int userId, String filterValue) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM transactions WHERE user_id = ?");

        boolean hasFilter = (filterValue != null && !filterValue.trim().isEmpty() && !filterValue.equals("ALL"));

        if (hasFilter) {
            if (filterValue.equals("DEPOSIT") || filterValue.equals("PURCHASE")) {
                sql.append(" AND type = ?");
            } else {
                sql.append(" AND status = ?");
            }
        }

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            ps.setInt(paramIndex++, userId);

            if (hasFilter) {
                ps.setString(paramIndex++, filterValue);
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
    public java.util.Map<String, Double> getUserOverview(int userId) {
        java.util.Map<String, Double> stats = new java.util.HashMap<>();
        // Tính tổng nạp (DEPOSIT + SUCCESS) và tổng tiêu (PURCHASE + SUCCESS)
        String sql = "SELECT " +
                "COALESCE(SUM(CASE WHEN type = 'DEPOSIT' AND status = 'SUCCESS' THEN amount ELSE 0 END), 0) as total_deposit, " +
                "COALESCE(SUM(CASE WHEN type = 'PURCHASE' AND status = 'SUCCESS' THEN amount ELSE 0 END), 0) as total_spent " +
                "FROM transactions WHERE user_id = ?";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.put("totalDeposit", rs.getDouble("total_deposit"));
                    stats.put("totalSpent", rs.getDouble("total_spent")); // total_spent trong DB là số âm, nhưng ở đây lấy giá trị raw
                    // Nếu muốn hiển thị số dương cho tổng tiêu thì có thể Math.abs() ở Servlet hoặc JSP
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    // 2. Đếm tổng số dòng (Có lọc Keyword + Ngày tháng) để phân trang
    public int countUserTransactions(int userId, String keyword, String type, String fromDate, String toDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM transactions WHERE user_id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (message LIKE ? OR order_id LIKE ?) ");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND type = ? ");
            params.add(type);
        }
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            sql.append(" AND DATE(created_at) >= ? ");
            params.add(fromDate);
        }
        if (toDate != null && !toDate.trim().isEmpty()) {
            sql.append(" AND DATE(created_at) <= ? ");
            params.add(toDate);
        }

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 3. Lấy danh sách giao dịch (Có lọc Keyword + Ngày tháng + Phân trang)
    public List<Transactions> getUserTransactions(int userId, String keyword, String type, String fromDate, String toDate, int page, int pageSize) {
        List<Transactions> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM transactions WHERE user_id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(userId);

        // --- Logic lọc ---
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (message LIKE ? OR order_id LIKE ?) ");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND type = ? ");
            params.add(type);
        }
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            sql.append(" AND DATE(created_at) >= ? ");
            params.add(fromDate);
        }
        if (toDate != null && !toDate.trim().isEmpty()) {
            sql.append(" AND DATE(created_at) <= ? ");
            params.add(toDate);
        }

        // Sắp xếp và phân trang
        sql.append(" ORDER BY created_at ASC LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transactions t = new Transactions();
                    t.setTransactionId(rs.getInt("transaction_id"));
                    t.setUserId(rs.getInt("user_id"));
                    // Xử lý order_id có thể null
                    int oId = rs.getInt("order_id");
                    if (!rs.wasNull()) {
                        t.setOrderId(oId);
                    }

                    t.setAmount(rs.getDouble("amount"));
                    t.setType(rs.getString("type"));
                    t.setStatus(rs.getString("status"));
                    t.setMessage(rs.getString("message"));
                    t.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}