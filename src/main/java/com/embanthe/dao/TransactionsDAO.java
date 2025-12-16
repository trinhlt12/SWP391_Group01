package com.embanthe.dao;
import com.embanthe.model.Transactions;
import com.embanthe.util.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class TransactionsDAO {
    // Trong file TransactionsDAO.java

    // 1. Hàm lấy danh sách có phân trang
    public List<Transactions> getRecentTransactions(int userId, int pageIndex, int pageSize) {
        List<Transactions> list = new ArrayList<>();

        int offset = (pageIndex - 1) * pageSize;

        // Câu lệnh SQL (Dành cho MySQL/MariaDB)
        String sql = "SELECT * FROM Transactions WHERE user_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, pageSize); // Lấy bao nhiêu dòng
            ps.setInt(3, offset);   // Bỏ qua bao nhiêu dòng đầu

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transactions t = new Transactions();
                t.setTransactionId(rs.getInt("transaction_id"));
                t.setAmount(rs.getDouble("amount"));
                t.setType(rs.getString("type"));
                t.setStatus(rs.getString("status"));
                t.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTransactionsByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM Transactions WHERE user_id = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
