package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voting {
    private int id;
    private int commission_id;
    private LocalDate endDate;
//    private String created_at;
    private List<Candidate> candidates = new ArrayList<>();
    private Map<RegularUser, Candidate> votes = new HashMap<>();

    public Voting(int id, int commission_id, LocalDate endDate) {
        this.id = id;
        this.commission_id = commission_id;
        this.endDate = endDate;
    }

    // Геттеры и методы для добавления
    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void addVote(RegularUser user, Candidate candidate) {
        votes.put(user, candidate);
    }

    public int getId() { return id; }
    public int getCommissionId() { return commission_id; }
    public LocalDate getEndDate() { return endDate; }
    public List<Candidate> getCandidates() { return candidates; }
}