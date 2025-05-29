# E-BallotBox API

Система электронного голосования с REST API интерфейсом (Практическое задание №4).

## Описание

Это веб-приложение на Spring Boot, которое предоставляет API для управления электронным голосованием. Система поддерживает:

- Создание и управление голосованиями
- Подачу голосов пользователями  
- Получение результатов голосования
- Экспорт результатов в PDF
- Кэширование данных с флагом read-only
- Ограничение подключений к БД (1 соединение)

## Требования

- Java 21+
- PostgreSQL 12+
- Maven 3.6+

## Настройка базы данных

1. Создайте базу данных PostgreSQL:

```sql
CREATE DATABASE "E-BallotBox";
```

2. Выполните SQL скрипт для создания таблиц:

```bash
psql -d E-BallotBox -f src/main/java/org/example/config/bd.sql
```

## Запуск приложения

```bash
mvn spring-boot:run
```

Приложение будет доступно по адресу: `http://localhost:8080/api`

## API Documentation

После запуска приложения документация API доступна по адресу:

- Swagger UI: `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api/docs`

## Основные эндпоинты

### Голосования

- `GET /api/voting/list` - получить все голосования
- `GET /api/voting/active?commissionId={id}` - получить активные голосования
- `GET /api/voting/{id}` - получить голосование по ID
- `POST /api/voting/vote` - подать голос

### Результаты

- `GET /api/voting/results/{votingId}` - получить результаты голосования
- `GET /api/voting/user/{userId}/votes` - получить голоса пользователя

### Экспорт

- `POST /api/export/pdf/single` - экспорт одного голосования в PDF
- `POST /api/export/pdf/multiple` - экспорт нескольких голосований в PDF

## Примеры использования

### Подача голоса

```bash
curl -X POST http://localhost:8080/api/voting/vote \
  -H "Content-Type: application/json" \
  -d '{
    "user_id": 1,
    "voting_id": 1,
    "candidate_id": 1
  }'
```

### Получение результатов

```bash
curl http://localhost:8080/api/voting/results/1
```

### Экспорт в PDF

```bash
curl -X POST "http://localhost:8080/api/export/pdf/single?votingId=1&fileName=results" \
  --output results.pdf
```

## Кэширование

Система использует Caffeine для кэширования:

- Данные с флагом `is_read_only = true` кэшируются автоматически
- Результаты голосований кэшируются на 30 минут
- PDF экспорты кэшируются на 2 часа

## Конфигурация

Основные настройки в `application.yml`:

- Порт сервера: 8080
- Контекст: `/api`
- Максимум соединений с БД: 1
- Директория для PDF: `./results/`

## Структура проекта

```
src/main/java/org/example/
├── controller/          # REST контроллеры
├── service/            # Бизнес-логика
├── repository/         # JPA репозитории
├── entity/            # JPA сущности
├── dto/               # Data Transfer Objects
├── config/            # Конфигурация
└── VotingSystemApplication.java
```
