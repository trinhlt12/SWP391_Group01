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
            ps.setString(4, request.getStatus() != null ? request.getStatus() : "Processing");
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

    public List<SupportRequests> getAll() throws SQLException {
        List<SupportRequests> requests = new ArrayList<>();
        String sql = "SELECT * FROM support_requests ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
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

    public SupportRequests getById(int requestId) throws SQLException {
        String sql = "SELECT * FROM support_requests WHERE request_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SupportRequests request = new SupportRequests();
                request.setRequestId(rs.getInt("request_id"));
                request.setUserId(rs.getInt("user_id"));
                request.setTitle(rs.getString("title"));
                request.setMessage(rs.getString("message"));
                request.setStatus(rs.getString("status"));
                request.setCreatedAt(rs.getTimestamp("created_at"));
                request.setProcessNote(rs.getString("process_note"));
                request.setProcessedAt(rs.getTimestamp("processed_at"));
                return request;
            }
        }
        return null;
    }

    public boolean updateProcess(int requestId, String processNote, String status) throws SQLException {
        String sql = "UPDATE support_requests " + "SET process_note = ?, status = ?, processed_at = NOW() " + "WHERE request_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, processNote);
            ps.setString(2, status); // phải là 'Processing', 'Approved' hoặc 'Rejected'
            ps.setInt(3, requestId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<SupportRequests> getBySubject(String subject) throws SQLException {
        String sql = "SELECT * FROM support_requests WHERE title = ? ORDER BY created_at DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, subject);
            ResultSet rs = ps.executeQuery();
            List<SupportRequests> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    public List<SupportRequests> getByStatus(String status) throws SQLException {
        String sql = "SELECT * FROM support_requests WHERE status = ? ORDER BY created_at DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            List<SupportRequests> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    public List<SupportRequests> getSupportRequests(int offset, int pageSize, String subject, String status) throws SQLException {
        List<SupportRequests> list = new ArrayList<>();
        String sql = "SELECT * FROM support_requests WHERE 1=1";

        if (subject != null && !subject.isEmpty()) {
            sql += " AND title = ?";
        }
        if (status != null && !status.isEmpty()) {
            sql += " AND status = ?";
        }

        sql += " ORDER BY created_at DESC LIMIT ? OFFSET ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        int index = 1;
        if (subject != null && !subject.isEmpty()) {
            ps.setString(index++, subject);
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(index++, status);
        }
        ps.setInt(index++, pageSize);
        ps.setInt(index, offset);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            SupportRequests req = new SupportRequests();
            req.setRequestId(rs.getInt("request_id"));
            req.setUserId(rs.getInt("user_id"));
            req.setTitle(rs.getString("title"));
            req.setMessage(rs.getString("message"));
            req.setStatus(rs.getString("status"));
            req.setCreatedAt(rs.getTimestamp("created_at"));
            req.setProcessedAt(rs.getTimestamp("processed_at"));
            list.add(req);
        }
        return list;
    }

    public int countSupportRequests(String subject, String status) throws SQLException {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM support_requests WHERE 1=1";

        if (subject != null && !subject.isEmpty()) {
            sql += " AND title = ?";
        }
        if (status != null && !status.isEmpty()) {
            sql += " AND status = ?";
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            if (subject != null && !subject.isEmpty()) {
                ps.setString(index++, subject);
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        }
        return total;
    }


    private SupportRequests mapRow(ResultSet rs) throws SQLException {
        SupportRequests sr = new SupportRequests();
        sr.setRequestId(rs.getInt("request_id"));
        sr.setUserId(rs.getInt("user_id"));
        sr.setTitle(rs.getString("title"));
        sr.setMessage(rs.getString("message"));
        sr.setProcessNote(rs.getString("process_note"));
        sr.setStatus(rs.getString("status"));
        sr.setCreatedAt(rs.getTimestamp("created_at"));
        sr.setProcessedAt(rs.getTimestamp("processed_at"));
        return sr;
    }

    public int countByUserAndStatus(int userId, String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM support_requests WHERE user_id = ?";
        if (status != null && !status.isEmpty()) {
            sql += " AND status = ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            if (status != null && !status.isEmpty()) {
                ps.setString(2, status);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<SupportRequests> getByUserWithPagingAndStatus(int userId, String status, int offset, int pageSize) throws SQLException {
        String sql = "SELECT * FROM support_requests WHERE user_id = ?";
        if (status != null && !status.isEmpty()) {
            sql += " AND status = ?";
        }
        sql += " ORDER BY created_at DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int idx = 1;
            ps.setInt(idx++, userId);
            if (status != null && !status.isEmpty()) {
                ps.setString(idx++, status);
            }
            ps.setInt(idx++, pageSize);
            ps.setInt(idx, offset);
            ResultSet rs = ps.executeQuery();
            List<SupportRequests> list = new ArrayList<>();
            while (rs.next()) {
                SupportRequests req = new SupportRequests();
                req.setRequestId(rs.getInt("request_id"));
                req.setUserId(rs.getInt("user_id"));
                req.setTitle(rs.getString("title"));
                req.setMessage(rs.getString("message"));
                req.setStatus(rs.getString("status"));
                req.setCreatedAt(rs.getTimestamp("created_at"));
                req.setProcessNote(rs.getString("process_note"));
                req.setProcessedAt(rs.getTimestamp("processed_at"));
                list.add(req);
            }
            return list;
        }
    }

}
