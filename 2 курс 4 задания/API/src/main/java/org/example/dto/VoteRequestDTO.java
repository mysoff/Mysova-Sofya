package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class VoteRequestDTO {
    
    @NotNull(message = "User ID обязателен")
    @JsonProperty("user_id")
    private Long userId;
    
    @NotNull(message = "Voting ID обязателен")
    @JsonProperty("voting_id")
    private Long votingId;
    
    @NotNull(message = "Candidate ID обязателен")
    @JsonProperty("candidate_id")
    private Long candidateId;
    
    // Constructors
    public VoteRequestDTO() {}
    
    public VoteRequestDTO(Long userId, Long votingId, Long candidateId) {
        this.userId = userId;
        this.votingId = votingId;
        this.candidateId = candidateId;
    }
    
    // Getters and Setters
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getVotingId() {
        return votingId;
    }
    
    public void setVotingId(Long votingId) {
        this.votingId = votingId;
    }
    
    public Long getCandidateId() {
        return candidateId;
    }
    
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
} 