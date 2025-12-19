package com.embanthe.dao;

import com.embanthe.model.SupportRequests;
import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupportRequestsDAO {

    private final Connection connection;

    public SupportRequestsDAO() throws SQLException {
        this.connection = DBContext.getInstance().getConnection();
    }


    public boolean sendSupport(SupportRequests request) {
        String sql = "INSERT INTO support_requests (user_id, title, message, status, created_at, process_note, processed_at) "
                + "VALUES (?, ?, ?, ?, ?,NULL,NULL)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (int) request.getUserId());
            ps.setString(2, request.getTitle());
            ps.setString(3, request.getMessage());
            ps.setString(4, request.getStatus() != null ? request.getStatus() : "OPEN");
            ps.setTimestamp(5, request.getCreatedAt());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countSupportRequestsByUser(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM support_requests WHERE user_id = ? AND DATE(created_at) = CURDATE()";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<SupportRequests> getAllByUser(int userId) throws SQLException {
        List<SupportRequests> requests = new ArrayList<>();
        String sql = "SELECT * FROM support_requests WHERE user_id = ? ORDER BY created_at DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SupportRequests request = new SupportRequests();
                request.setRequestId(rs.getInt("request_id"));
                request.setUserId(rs.getInt("user_id"));
                request.setTitle(rs.getString("title"));
                request.setMessage(rs.getString("message"));
                request.setStatus(rs.getString("status"));
                request.setCreatedAt(rs.getTimestamp("created_at"));
                request.setProcessNote(rs.getString("process_note"));
                request.setProcessedAt(rs.getTimestamp("processed_at"));
                requests.add(request);
            }
        }
        return requests;
    }
}
