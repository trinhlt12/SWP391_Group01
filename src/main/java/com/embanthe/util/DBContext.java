package com.embanthe.util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBContext {
    private static DBContext instance;
    private Connection connection;
    private String url;
    private String user;
    private String password;
    private String driver;

    private DBContext() {
        try {
            Properties props = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("db.properties not found in resources folder");
            }

            props.load(input);
            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");
            this.driver = props.getProperty("db.driver");

            Class.forName(driver);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties", e);
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
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Test connection
    public static void main(String[] args) {
        try {
            Connection conn = DBContext.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database connection SUCCESS!");
                System.out.println("Connected to: " + conn.getMetaData().getURL());
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection FAILED!");
            e.printStackTrace();
        }
    }
}