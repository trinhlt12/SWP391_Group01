package com.embanthe.dao;

import com.embanthe.model.Providers;
import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderDAO {

    // CREATE
    public void insert(Providers p) {
        String sql = "INSERT INTO providers (provider_name, logo_url) VALUES (?, ?)";

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getProviderName());
            ps.setString(2, p.getLogoUrl());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ ALL
    public List<Providers> getAll() {
        List<Providers> list = new ArrayList<>();
        String sql = "SELECT * FROM providers";

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Providers p = new Providers();
                p.setProviderId(rs.getInt("provider_id"));
                p.setProviderName(rs.getString("provider_name"));
                p.setLogoUrl(rs.getString("logo_url"));
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // READ BY ID
    public Providers getById(int id) {
        String sql = "SELECT * FROM providers WHERE provider_id = ?";
        Providers p = null;

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Providers();
                p.setProviderId(rs.getInt("provider_id"));
                p.setProviderName(rs.getString("provider_name"));
                p.setLogoUrl(rs.getString("logo_url"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    // UPDATE
    public void update(Providers p) {
        String sql = "UPDATE providers SET provider_name=?, logo_url=? WHERE provider_id=?";

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getProviderName());
            ps.setString(2, p.getLogoUrl());
            ps.setInt(3, p.getProviderId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM providers WHERE provider_id=?";

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
