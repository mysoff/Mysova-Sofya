package org.example.model;

public class VotingResult {
    private final String candidateName;
    private final int votes;

    public VotingResult(String candidateName, int votes) {
        this.candidateName = candidateName;
        this.votes = votes;
    }

    public String getCandidateName() { return candidateName; }
    public int getVotes() { return votes; }
}