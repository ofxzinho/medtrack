package com.medtrack.repository;

import com.medtrack.db.DatabaseConnection;
import com.medtrack.model.Caregiver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaregiverRepository {

    public void save(Caregiver caregiver) {
        String sql = "INSERT INTO caregivers (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, caregiver.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cuidador: " + e.getMessage());
        }
    }

    public List<Caregiver> findAll() {
        List<Caregiver> list = new ArrayList<>();
        String sql = "SELECT id, name FROM caregivers";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Caregiver(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cuidadores: " + e.getMessage());
        }
        return list;
    }

    public Optional<Caregiver> findById(int id) {
        String sql = "SELECT id, name FROM caregivers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Caregiver(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cuidador: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM caregivers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover cuidador: " + e.getMessage());
        }
    }
}
