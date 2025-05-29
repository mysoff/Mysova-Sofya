package org.example.service;

import org.example.config.DatabaseConnection;
import org.example.model.VotingResult;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateService {
    // Обновление информации о кандидате
    public boolean updateCandidateInfo(int candidateId, String newInfo) {
        String sql = "UPDATE candidates SET info = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newInfo);
            stmt.setInt(2, candidateId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Получение результатов голосований для кандидата
    public List<VotingResult> getPreviousResults(int candidateId) {
        List<VotingResult> results = new ArrayList<>();
        String sql = "SELECT v.id AS voting_id, COUNT(vt.id) AS votes " +
                "FROM votings v " +
                "JOIN votes vt ON v.id = vt.voting_id " +
                "WHERE vt.candidate_id = ? AND v.end_date < CURRENT_DATE " +
                "GROUP BY v.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, candidateId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new VotingResult(
                        "Голосование #" + rs.getInt("voting_id"),
                        rs.getInt("votes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Получение списка всех голосований кандидата
    public List<String> getParticipatedVotings(int candidateId) {
        List<String> votings = new ArrayList<>();
        String sql = "SELECT v.id, v.end_date " +
                "FROM candidate_voting cv " +
                "JOIN votings v ON cv.voting_id = v.id " +
                "WHERE cv.candidate_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, candidateId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                votings.add(
                        "ID: " + rs.getInt("id") +
                                " | Дата окончания: " + rs.getDate("end_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votings;
    }
}