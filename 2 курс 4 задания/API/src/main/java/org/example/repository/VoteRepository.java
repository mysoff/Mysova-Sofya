package org.example.repository;

import org.example.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    
    Optional<VoteEntity> findByUserIdAndVotingId(Long userId, Long votingId);
    
    List<VoteEntity> findByVotingId(Long votingId);
    
    List<VoteEntity> findByUserId(Long userId);
    
    @Query("SELECT v.candidateId, COUNT(v) FROM VoteEntity v WHERE v.votingId = :votingId GROUP BY v.candidateId")
    List<Object[]> getVotingResults(@Param("votingId") Long votingId);
    
    @Query("SELECT v.candidateId, COUNT(v) FROM VoteEntity v WHERE v.votingId IN :votingIds GROUP BY v.candidateId")
    List<Object[]> getMultipleVotingResults(@Param("votingIds") List<Long> votingIds);
    
    @Query("SELECT v FROM VoteEntity v WHERE v.isReadOnly = true")
    List<VoteEntity> findCacheableVotes();
} 