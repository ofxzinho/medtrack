package com.medtrack.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = System.getenv("DB_URL");

    public static Connection getConnection() throws SQLException {
        if (URL == null) {
            throw new SQLException("DB_URL não definida.");
        }
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new SQLException("Erro de conexão: " + e.getMessage()
                    + " | URL usada: " + URL);
        }
    }
}
