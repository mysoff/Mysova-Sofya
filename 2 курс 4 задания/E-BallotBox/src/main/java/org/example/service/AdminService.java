package org.example.service;

import org.example.config.DatabaseConnection;
import org.example.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    // Просмотр всех пользователей
    public List<RegularUser> getAllUsers() {
        List<RegularUser> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                RegularUser user = new RegularUser(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("snils")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Удаление пользователя
    public boolean deleteUser(String login) {
        String sql = "DELETE FROM users WHERE login = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Просмотр всех ЦИК
    public List<CEC> getAllCECs() {
        List<CEC> cecs = new ArrayList<>();
        String sql = "SELECT * FROM central_election_commissions";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cecs.add(new CEC(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getInt("created_by_admin_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cecs;
    }

    // Удаление ЦИК
    public boolean deleteCEC(String login) {
        String sql = "DELETE FROM central_election_commissions WHERE login = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Создание ЦИК
    public boolean createCEC(String login, String password, int adminId) {
        String sql = "INSERT INTO central_election_commissions (login, password, created_by_admin_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setInt(3, adminId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Просмотр кандидатов
    public List<Candidate> getCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                candidates.add(new Candidate(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("info"),
                        rs.getString("full_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    // Удаление кандидата
    public boolean deleteCandidate(String login) {
        String sql = "DELETE FROM candidates WHERE login = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}