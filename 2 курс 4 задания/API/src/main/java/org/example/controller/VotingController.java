package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.VoteRequestDTO;
import org.example.dto.VotingResultDTO;
import org.example.entity.VoteEntity;
import org.example.entity.VotingEntity;
import org.example.repository.VoteRepository;
import org.example.repository.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/voting")
@Tag(name = "Voting API", description = "API для управления голосованиями")
@Validated
public class VotingController {

    @Autowired
    private VotingRepository votingRepository;
    
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/list")
    @Operation(summary = "Получить список всех голосований")
    public ResponseEntity<List<VotingEntity>> getAllVotings() {
        List<VotingEntity> votings = votingRepository.findAll();
        return ResponseEntity.ok(votings);
    }

    @GetMapping("/active")
    @Operation(summary = "Получить активные голосования")
    public ResponseEntity<List<VotingEntity>> getActiveVotings(@RequestParam Long commissionId) {
        List<VotingEntity> activeVotings = votingRepository.findActiveVotingsByCommissionId(
            commissionId, LocalDate.now());
        return ResponseEntity.ok(activeVotings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить голосование по ID")
    @Cacheable(value = "votings", condition = "#result.body != null && #result.body.isReadOnly == true")
    public ResponseEntity<VotingEntity> getVotingById(@PathVariable Long id) {
        Optional<VotingEntity> voting = votingRepository.findById(id);
        return voting.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vote")
    @Operation(summary = "Подать голос")
    public ResponseEntity<String> vote(@Valid @RequestBody VoteRequestDTO voteRequest) {
        // Проверяем, не голосовал ли уже пользователь
        Optional<VoteEntity> existingVote = voteRepository.findByUserIdAndVotingId(
            voteRequest.getUserId(), voteRequest.getVotingId());
        
        if (existingVote.isPresent()) {
            return ResponseEntity.badRequest().body("Пользователь уже проголосовал в этом голосовании");
        }

        // Проверяем, что голосование активно
        Optional<VotingEntity> voting = votingRepository.findById(voteRequest.getVotingId());
        if (voting.isEmpty() || !voting.get().isActive()) {
            return ResponseEntity.badRequest().body("Голосование неактивно или не существует");
        }

        // Создаём новый голос
        VoteEntity vote = new VoteEntity(
            voteRequest.getUserId(),
            voteRequest.getVotingId(),
            voteRequest.getCandidateId()
        );
        
        voteRepository.save(vote);
        return ResponseEntity.ok("Голос успешно подан");
    }

    @GetMapping("/results/{votingId}")
    @Operation(summary = "Получить результаты голосования")
    @Cacheable(value = "voting-results", key = "#votingId")
    public ResponseEntity<List<VotingResultDTO>> getVotingResults(@PathVariable Long votingId) {
        List<Object[]> results = voteRepository.getVotingResults(votingId);
        
        List<VotingResultDTO> resultDTOs = results.stream()
            .map(result -> new VotingResultDTO(
                (Long) result[0],           // candidateId
                "Кандидат " + result[0],    // candidateName (упрощённо)
                (Long) result[1],           // votesCount
                votingId
            ))
            .toList();
            
        return ResponseEntity.ok(resultDTOs);
    }

    @GetMapping("/user/{userId}/votes")
    @Operation(summary = "Получить все голоса пользователя")
    public ResponseEntity<List<VoteEntity>> getUserVotes(@PathVariable Long userId) {
        List<VoteEntity> votes = voteRepository.findByUserId(userId);
        return ResponseEntity.ok(votes);
    }
} 