package io.wisoft.shop.util.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String driver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/shopdatabase";
    private static final String user = "jun";
    private static final String pwd = "2457";

    protected Connection getConnection() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
