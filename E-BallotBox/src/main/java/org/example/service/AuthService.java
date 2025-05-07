package org.example.service;

import org.example.model.*;
import org.example.config.DatabaseConnection;
import java.sql.*;

public class AuthService {
    public User authenticate(String login, String password) {
        String sql = """
            SELECT 'admin' AS role, id, login, password, full_name, NULL AS info, NULL AS snils, NULL AS created_by_admin_id 
            FROM admins WHERE login = ? AND password = ?
            UNION
            SELECT 'cec' AS role, id, login, password, NULL AS full_name, NULL AS info, NULL AS snils, created_by_admin_id 
            FROM central_election_commissions WHERE login = ? AND password = ?
            UNION
            SELECT 'candidate' AS role, id, login, password, full_name, info, NULL AS snils, NULL AS created_by_admin_id 
            FROM candidates WHERE login = ? AND password = ?
            UNION
            SELECT 'user' AS role, id, login, password, full_name, NULL AS info, NULL AS snils, NULL AS created_by_admin_id 
            FROM users WHERE login = ? AND password = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Устанавливаем параметры для каждого из 4 UNION-блоков
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, login);
            stmt.setString(4, password);
            stmt.setString(5, login);
            stmt.setString(6, password);
            stmt.setString(7, login);
            stmt.setString(8, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                int id = rs.getInt("id");
                String userLogin = rs.getString("login");
                String fullName = rs.getString("full_name");
                String info = rs.getString("info");
                String snils = rs.getString("snils");
                Integer createdByAdminId = rs.getObject("created_by_admin_id", Integer.class);

                return switch (role) {
                    case "admin" -> new Admin(id, userLogin, password, fullName);
                    case "cec" -> new CEC(id, userLogin, password, createdByAdminId);
                    case "candidate" -> new Candidate(id, userLogin, password, info, fullName);
                    case "user" -> new RegularUser(id, userLogin, password, fullName, snils);
                    default -> throw new IllegalStateException("Unknown role: " + role);
                };
            }
        } catch (SQLException e) {
            // Лучше использовать логгер вместо printStackTrace
            System.err.println("Authentication error: " + e.getMessage());
        }
        return null;
    }


//    public boolean registerUser(User user) {
//        String sql = """
//            INSERT INTO users (full_name, date_of_birth, snils, login, password)
//            VALUES (?, ?, ?, ?, ?)
//        """;
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, user.getFullName());
//            stmt.setDate(2, Date.valueOf(user.getDateOfBirth()));
//            stmt.setString(3, user.getSnils());
//            stmt.setString(4, user.getLogin());
//            stmt.setString(5, user.getPassword());
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}