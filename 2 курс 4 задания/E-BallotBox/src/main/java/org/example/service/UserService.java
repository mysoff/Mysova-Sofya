package org.example.service;

import org.example.config.DatabaseConnection;
import org.example.model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    // Регистрация пользователя
    public boolean registerUser(String fullName, LocalDate dateOfBirth, String snils, String login, String password) {
        String sql = "INSERT INTO users (full_name, date_of_birth, snils, login, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setDate(2, Date.valueOf(dateOfBirth));
            stmt.setString(3, snils);
            stmt.setString(4, login);
            stmt.setString(5, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Голосование
    // метод для получения активных голосований
    public List<Voting> getActiveVotings() {
        List<Voting> votings = new ArrayList<>();
        String sql = "SELECT id, commission_id, end_date FROM votings WHERE end_date >= CURRENT_DATE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                votings.add(new Voting(
                        rs.getInt("id"),
                        rs.getInt("commission_id"),
                        rs.getDate("end_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votings;
    }

    // метод для получения кандидатов голосования
    public List<Candidate> getCandidatesForVoting(int votingId) {
        List<Candidate> candidates = new ArrayList<>();
        String sql = """
        SELECT c.id, c.login, c.password, c.info, c.full_name
        FROM candidates c 
        JOIN candidate_voting cv ON c.id = cv.candidate_id 
        WHERE cv.voting_id = ?
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, votingId);
            ResultSet rs = stmt.executeQuery();
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

    // Обновленный метод голосования
    public boolean vote(int userId, int votingId, int candidateId) {
        String sql = "INSERT INTO votes (user_id, voting_id, candidate_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, votingId);
            stmt.setInt(3, candidateId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                System.out.println("✗ Вы уже голосовали в этом голосовании!");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    // Получение списка кандидатов
    public List<String> getActiveCandidates() {
        List<String> candidates = new ArrayList<>();
        // Получаем активные голосования
        String sqlActiveVotings = "SELECT id FROM votings WHERE end_date >= CURRENT_DATE";
        // Получаем кандидатов для активных голосований
        String sqlCandidates = """
            SELECT DISTINCT c.full_name, cv.voting_id 
            FROM candidates c 
            JOIN candidate_voting cv ON c.id = cv.candidate_id 
            WHERE cv.voting_id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtVotings = conn.prepareStatement(sqlActiveVotings);
             ResultSet rsVotings = stmtVotings.executeQuery()) {

            // Для каждого активного голосования
            while (rsVotings.next()) {
                int votingId = rsVotings.getInt("id");

                try (PreparedStatement stmtCandidates = conn.prepareStatement(sqlCandidates)) {
                    stmtCandidates.setInt(1, votingId);
                    ResultSet rsCandidates = stmtCandidates.executeQuery();

                    // Собираем кандидатов
                    while (rsCandidates.next()) {
                        String candidateName = rsCandidates.getString("full_name");
//                        int votingId = rsCandidates.getInt("voting_id");
                        candidates.add(candidateName + " (Голосование #" + votingId + ")");
//                        if (!candidates.contains(candidateName)) {
//                            candidates.add(candidateName);
//                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    // История голосований пользователя
    public List<String> getVotingHistory(int userId) {
        List<String> history = new ArrayList<>();
        String sql = "SELECT v.id, v.end_date, CASE WHEN vt.user_id IS NULL THEN 'Не голосовал' ELSE 'Голосовал' END AS status " +
                "FROM votings v LEFT JOIN votes vt ON v.id = vt.voting_id AND vt.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                history.add(
                        "Голосование #" + rs.getInt("id") +
                                " | Дата окончания: " + rs.getDate("end_date") +
                                " | Статус: " + rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}