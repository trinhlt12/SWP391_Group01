package com.embanthe.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBContext {
    private static DBContext instance;
    private String url;
    private String user;
    private String password;
    private String driver;

    private DBContext() {
        try {
            Properties props = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("❌ ERROR: db.properties not found in resources folder");
            }

            props.load(input);
            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");
            this.driver = props.getProperty("db.driver");

            // Load Driver
            Class.forName(driver);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ ERROR: MySQL Driver not found", e);
        } catch (IOException e) {
            throw new RuntimeException("❌ ERROR: Failed to load db.properties", e);
        }
    }

    public static DBContext getInstance() {
        if (instance == null) {
            synchronized (DBContext.class) {
                if (instance == null) {
                    instance = new DBContext();
                }
            }
        }
        return instance;
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Main test
    public static void main(String[] args) {
        try (Connection conn = DBContext.getInstance().getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database connection SUCCESS!");
                System.out.println("Connected to: " + conn.getMetaData().getURL());
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection FAILED!");
            e.printStackTrace();
        }
    }
}