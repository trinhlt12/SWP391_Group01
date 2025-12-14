package com.embanthe.dao;

import com.embanthe.model.Transactions;
import com.embanthe.util.DBContext;

import java.sql.*;

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
}