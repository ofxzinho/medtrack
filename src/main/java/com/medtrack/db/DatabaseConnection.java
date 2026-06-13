package com.medtrack.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DEFAULT_URL =
            "jdbc:postgresql://localhost:5432/medtrack?user=postgres&password=sua_senha_local";

    private static final String URL =
            System.getenv("DB_URL") != null ? System.getenv("DB_URL") : DEFAULT_URL;

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new SQLException("Erro de conexão: " + e.getMessage() + " | URL usada: " + URL);
        }
    }
}
