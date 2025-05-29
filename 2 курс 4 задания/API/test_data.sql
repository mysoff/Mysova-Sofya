-- Тестовые данные для демонстрации API

-- Добавляем тестовые голосования
INSERT INTO votings (commission_id, end_date, created_at, is_read_only) VALUES
(1, '2025-12-31', CURRENT_TIMESTAMP, false),
(2, '2025-06-01', CURRENT_TIMESTAMP, false),
(1, '2024-01-01', CURRENT_TIMESTAMP, true);

-- Добавляем тестовых пользователей
INSERT INTO users (full_name, date_of_birth, snils, login, password, is_read_only) VALUES
('Иван Иванов', '1990-01-01', '123-456-789-01', 'ivan', 'password123', false),
('Петр Петров', '1985-05-15', '123-456-789-02', 'petr', 'password123', false),
('Анна Сидорова', '1992-08-20', '123-456-789-03', 'anna', 'password123', false);

-- Добавляем тестовые голоса
INSERT INTO votes (user_id, voting_id, candidate_id, voted_at, is_read_only) VALUES
(1, 1, 101, CURRENT_TIMESTAMP, true),
(2, 1, 102, CURRENT_TIMESTAMP, true),
(3, 1, 101, CURRENT_TIMESTAMP, true),
(1, 2, 201, CURRENT_TIMESTAMP, true),
(2, 3, 301, CURRENT_TIMESTAMP, true); 