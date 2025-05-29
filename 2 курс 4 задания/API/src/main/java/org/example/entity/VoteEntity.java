package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class VoteEntity extends BaseEntity {
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "voting_id", nullable = false)
    private Long votingId;
    
    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;
    
    @Column(name = "voted_at")
    private LocalDateTime votedAt;
    
    // Constructors
    public VoteEntity() {
        this.votedAt = LocalDateTime.now();
        this.setIsReadOnly(true); // голоса по умолчанию read-only
    }
    
    public VoteEntity(Long userId, Long votingId, Long candidateId) {
        this();
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
    
    public LocalDateTime getVotedAt() {
        return votedAt;
    }
    
    public void setVotedAt(LocalDateTime votedAt) {
        this.votedAt = votedAt;
    }
} 