package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "votings")
public class VotingEntity extends BaseEntity {
    
    @Column(name = "commission_id", nullable = false)
    private Long commissionId;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public VotingEntity() {
        this.createdAt = LocalDateTime.now();
    }
    
    public VotingEntity(Long commissionId, LocalDate endDate) {
        this();
        this.commissionId = commissionId;
        this.endDate = endDate;
    }
    
    // Getters and Setters
    public Long getCommissionId() {
        return commissionId;
    }
    
    public void setCommissionId(Long commissionId) {
        this.commissionId = commissionId;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isActive() {
        return endDate.isAfter(LocalDate.now()) || endDate.isEqual(LocalDate.now());
    }
} 