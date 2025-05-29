package org.example.repository;

import org.example.entity.VotingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VotingRepository extends JpaRepository<VotingEntity, Long> {
    
    List<VotingEntity> findByCommissionId(Long commissionId);
    
    @Query("SELECT v FROM VotingEntity v WHERE v.commissionId = :commissionId AND v.endDate >= :currentDate")
    List<VotingEntity> findActiveVotingsByCommissionId(@Param("commissionId") Long commissionId, 
                                                        @Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT v FROM VotingEntity v WHERE v.isReadOnly = true")
    List<VotingEntity> findCacheableVotings();
} 