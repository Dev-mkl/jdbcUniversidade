package org.example.factory;

import java.sql.*;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/universidade";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Mmfc18!postgre";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
