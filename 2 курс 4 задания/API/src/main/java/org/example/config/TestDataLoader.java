package org.example.config;

import org.example.entity.UserEntity;
import org.example.entity.VoteEntity;
import org.example.entity.VotingEntity;
import org.example.repository.VoteRepository;
import org.example.repository.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Profile("test")
public class TestDataLoader implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private VotingRepository votingRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("🚀 Загружаю тестовые данные...");

        // Создаём тестовые голосования
        VotingEntity voting1 = new VotingEntity(1L, LocalDate.of(2025, 12, 31));
        VotingEntity voting2 = new VotingEntity(2L, LocalDate.of(2025, 6, 1));
        VotingEntity voting3 = new VotingEntity(1L, LocalDate.of(2024, 1, 1));
        voting3.setIsReadOnly(true);

        voting1 = votingRepository.save(voting1);
        voting2 = votingRepository.save(voting2);
        voting3 = votingRepository.save(voting3);

        // Создаём тестовых пользователей
        UserEntity user1 = new UserEntity("Иван Иванов", LocalDate.of(1990, 1, 1), "123-456-789-01", "ivan", "password123");
        UserEntity user2 = new UserEntity("Петр Петров", LocalDate.of(1985, 5, 15), "123-456-789-02", "petr", "password123");
        UserEntity user3 = new UserEntity("Анна Сидорова", LocalDate.of(1992, 8, 20), "123-456-789-03", "anna", "password123");

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);
        entityManager.flush();

        // Создаём тестовые голоса
        VoteEntity vote1 = new VoteEntity(user1.getId(), voting1.getId(), 101L);
        VoteEntity vote2 = new VoteEntity(user2.getId(), voting1.getId(), 102L);
        VoteEntity vote3 = new VoteEntity(user3.getId(), voting1.getId(), 101L);
        VoteEntity vote4 = new VoteEntity(user1.getId(), voting2.getId(), 201L);
        VoteEntity vote5 = new VoteEntity(user2.getId(), voting3.getId(), 301L);

        voteRepository.save(vote1);
        voteRepository.save(vote2);
        voteRepository.save(vote3);
        voteRepository.save(vote4);
        voteRepository.save(vote5);

        System.out.println("✅ Тестовые данные загружены успешно!");
        System.out.println("📊 Создано голосований: " + votingRepository.count());
        System.out.println("🗳️  Создано голосов: " + voteRepository.count());
    }
} 