package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.System.out;

public class DatabaseConnection {
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

class DatabaseTest {
    static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
