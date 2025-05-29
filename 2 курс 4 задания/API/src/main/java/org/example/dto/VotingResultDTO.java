package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VotingResultDTO {
    
    @JsonProperty("candidate_id")
    private Long candidateId;
    
    @JsonProperty("candidate_name")
    private String candidateName;
    
    @JsonProperty("votes_count")
    private Long votesCount;
    
    @JsonProperty("voting_id")
    private Long votingId;
    
    // Constructors
    public VotingResultDTO() {}
    
    public VotingResultDTO(Long candidateId, String candidateName, Long votesCount) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.votesCount = votesCount;
    }
    
    public VotingResultDTO(Long candidateId, String candidateName, Long votesCount, Long votingId) {
        this(candidateId, candidateName, votesCount);
        this.votingId = votingId;
    }
    
    // Getters and Setters
    public Long getCandidateId() {
        return candidateId;
    }
    
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
    
    public String getCandidateName() {
        return candidateName;
    }
    
    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
    
    public Long getVotesCount() {
        return votesCount;
    }
    
    public void setVotesCount(Long votesCount) {
        this.votesCount = votesCount;
    }
    
    public Long getVotingId() {
        return votingId;
    }
    
    public void setVotingId(Long votingId) {
        this.votingId = votingId;
    }
} 