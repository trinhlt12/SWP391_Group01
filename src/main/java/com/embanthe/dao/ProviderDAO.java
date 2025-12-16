package com.embanthe. dao;

import com.embanthe.model.Providers;
import com.embanthe.util.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProviderDAO {

    // Lấy tất cả providers
    public List<Providers> getAll() {
        List<Providers> list = new ArrayList<>();
        String sql = "SELECT * FROM providers ORDER BY provider_id DESC";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps. executeQuery()) {
            while (rs.next()) {
                Providers p = new Providers();
                p.setProviderId(rs.getInt("provider_id"));
                p.setProviderName(rs.getString("provider_name"));
                p.setLogoUrl(rs.getString("logo_url"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh sách có phân trang và tìm kiếm
    public List<Providers> getPagedList(String search, int offset, int pageSize) {
        List<Providers> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM providers WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (search != null && !search. trim().isEmpty()) {
            sql.append("AND provider_name LIKE ? ");
            params.add("%" + search. trim() + "%");
        }
        sql.append("ORDER BY provider_id DESC LIMIT ?  OFFSET ?");
        params.add(pageSize);
        params.add(offset);

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Providers p = new Providers();
                    p.setProviderId(rs.getInt("provider_id"));
                    p.setProviderName(rs.getString("provider_name"));
                    p.setLogoUrl(rs. getString("logo_url"));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đếm số lượng
    public int count(String search) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM providers WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (search != null && !search. trim().isEmpty()) {
            sql.append("AND provider_name LIKE ? ");
            params.add("%" + search.trim() + "%");
        }
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps. setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy provider theo ID
    public Providers getById(int id) {
        String sql = "SELECT * FROM providers WHERE provider_id = ?";
        try (Connection con = DBContext. getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Providers p = new Providers();
                    p.setProviderId(rs.getInt("provider_id"));
                    p.setProviderName(rs.getString("provider_name"));
                    p.setLogoUrl(rs.getString("logo_url"));
                    return p;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm provider
    public void insert(Providers p) {
        String sql = "INSERT INTO providers (provider_name, logo_url) VALUES (?, ?)";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getProviderName());
            ps.setString(2, p.getLogoUrl());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật provider
    public void update(Providers p) {
        String sql = "UPDATE providers SET provider_name = ?, logo_url = ? WHERE provider_id = ?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getProviderName());
            ps.setString(2, p.getLogoUrl());
            ps.setInt(3, p.getProviderId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa provider
    public void delete(int id) {
        String sql = "DELETE FROM providers WHERE provider_id = ? ";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}