package com.medtrack.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Se a variável DB_URL não existir (rodando local), ele usa o banco da máquina de vocês.
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/medtrack?user=postgres&password=sua_senha_local";
    
    // Lê da nuvem, se for nulo, usa o padrão local
    private static final String URL = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : DEFAULT_URL;

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new SQLException("Erro de conexão: " + e.getMessage() + " | URL usada: " + URL);
        }
    }
}
