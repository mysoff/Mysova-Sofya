package org.example.service;

import org.example.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VotingService {

    public List<String> getActiveVotings() {
        List<String> votings = new ArrayList<>();
        String sql = "SELECT id, end_date FROM votings WHERE end_date >= CURRENT_DATE";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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

//    public boolean createVoting(int commissionId, Date endDate) {
//        String sql = """
//            INSERT INTO votings (commission_id, end_date)
//            VALUES (?, ?)
//        """;
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, commissionId);
//            stmt.setDate(2, endDate);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public boolean addCandidateToVoting(int votingId, int candidateId) {
//        String sql = """
//            INSERT INTO candidate_voting (candidate_id, voting_id)
//            VALUES (?, ?)
//        """;
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, candidateId);
//            stmt.setInt(2, votingId);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}