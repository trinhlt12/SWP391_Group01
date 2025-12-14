package com.embanthe.dao;
import com.embanthe.model.Transactions;
import com.embanthe.util.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class TransactionsDAO {
    public List<Transactions> getRecentTransactions(int userId) {
        List<Transactions> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY created_at DESC LIMIT 10";

        // --- SỬA LỖI: new DBContext().getConnection() ---
        try (Connection conn =  DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
