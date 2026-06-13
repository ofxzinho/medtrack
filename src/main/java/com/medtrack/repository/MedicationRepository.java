package com.medtrack.repository;

import com.medtrack.db.DatabaseConnection;
import com.medtrack.model.Caregiver;
import com.medtrack.model.Medication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicationRepository {

    public void save(Medication medication) {
        String sql = "INSERT INTO medications (name, dosage, schedule_time, caregiver_id, taken)"
                + " VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medication.getName());
            stmt.setString(2, medication.getDosage());
            stmt.setString(3, medication.getScheduleTime());
            stmt.setInt(4, medication.getCaregiver().getId());
            stmt.setBoolean(5, medication.isTaken());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar medicamento: " + e.getMessage());
        }
    }

    public List<Medication> findAll() {
        List<Medication> list = new ArrayList<>();
        String sql = "SELECT m.id, m.name, m.dosage, m.schedule_time, m.taken,"
                + " c.id AS caregiver_id, c.name AS caregiver_name"
                + " FROM medications m JOIN caregivers c ON m.caregiver_id = c.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Caregiver caregiver = new Caregiver(
                        rs.getInt("caregiver_id"),
                        rs.getString("caregiver_name"));
                Medication med = new Medication(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("dosage"),
                        rs.getString("schedule_time"),
                        caregiver);
                med.setTaken(rs.getBoolean("taken"));
                list.add(med);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar medicamentos: " + e.getMessage());
        }
        return list;
    }

    public Optional<Medication> findById(int id) {
        String sql = "SELECT m.id, m.name, m.dosage, m.schedule_time, m.taken,"
                + " c.id AS caregiver_id, c.name AS caregiver_name"
                + " FROM medications m JOIN caregivers c ON m.caregiver_id = c.id"
                + " WHERE m.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Caregiver caregiver = new Caregiver(
                        rs.getInt("caregiver_id"),
                        rs.getString("caregiver_name"));
                Medication med = new Medication(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("dosage"),
                        rs.getString("schedule_time"),
                        caregiver);
                med.setTaken(rs.getBoolean("taken"));
                return Optional.of(med);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar medicamento: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM medications WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar medicamento: " + e.getMessage());
        }
    }

    public void updateTaken(int id, boolean taken) {
        String sql = "UPDATE medications SET taken = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, taken);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar medicamento: " + e.getMessage());
        }
    }
}
