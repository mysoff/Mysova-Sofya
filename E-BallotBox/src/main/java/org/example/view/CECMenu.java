package org.example.view;

import org.example.model.*;
import org.example.service.CECService;
import org.example.utils.ConsoleDesign;
import org.example.utils.InputUtils;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CECMenu {
    private final CECService cecService;
    private final int currentCECId;

    public CECMenu(CECService cecService, int cecId) {
        this.cecService = cecService;
        this.currentCECId = cecId;
    }

    public void showMenu() {
        ConsoleDesign.clearScreen();
        while (true) {
            ConsoleDesign.printHeader("МЕНЮ ЦИК");
            ConsoleDesign.printMenuBox(
                    "1. Создать голосование",
                    "2. Создать кандидата",
                    "3. Привязать кандидата к голосованию",
                    "4. Просмотр результатов",
                    "5. Экспорт в PDF",
                    "0. Выход"
            );
            String choice = InputUtils.readLineColored("▸ Выберите действие: ", ConsoleDesign.YELLOW);

            switch (choice) {
                case "1" -> createVoting();
                case "2" -> addCandidate();
                case "3" -> assignCandidateToVoting();
                case "4" -> showResults();
                case "5" -> exportToPDF();
                case "0" -> {
                    ConsoleDesign.clearScreen();
                    return;
                }
                default -> ConsoleDesign.printError("Неверная команда!");
            }
        }
    }

    private void assignCandidateToVoting() {
        ConsoleDesign.clearScreen();
        List<Voting> votings = cecService.getActiVotings(currentCECId);
        ConsoleDesign.printSubHeader("ДОСТУПНЫЕ ГОЛОСОВАНИЯ");
        votings.forEach(v -> System.out.printf(
                ConsoleDesign.CYAN + "ID: %d | Дата окончания: %s\n" + ConsoleDesign.RESET,
                v.getId(),
                v.getEndDate()
        ));

        int votingId = InputUtils.readIntColored("▸ Введите ID голосования: ", ConsoleDesign.BLUE);

        List<Candidate> candidates = cecService.getAllCandidates(currentCECId);
        ConsoleDesign.printSubHeader("ДОСТУПНЫЕ КАНДИДАТЫ");
        candidates.forEach(c -> System.out.printf(
                ConsoleDesign.CYAN + "ID: %d | %s\n" + ConsoleDesign.RESET,
                c.getId(),
                c.getFullName()
        ));

        int candidateId = InputUtils.readIntColored("▸ Введите ID кандидата: ", ConsoleDesign.BLUE);
        boolean success = cecService.assignCandidateToVoting(candidateId, votingId);
        if (success) ConsoleDesign.printSuccess("Кандидат привязан!");
        else ConsoleDesign.printError("Ошибка привязки!");
    }

    private void showResults() {
        ConsoleDesign.clearScreen();

        // Получаем список доступных голосований
        List<Voting> votings = cecService.getAllVotings(currentCECId);
        if (votings.isEmpty()) {
            ConsoleDesign.printErrorBox("Нет голосований!");
            return;
        }

        // Выводим список ID голосований
        ConsoleDesign.printSubHeader("ВСЕ ГОЛОСОВАНИЯ");
        votings.forEach(v -> System.out.printf(
                ConsoleDesign.CYAN + "ID: %d | Дата окончания: %s\n" + ConsoleDesign.RESET,
                v.getId(),
                v.getEndDate()
        ));

        int votingId = InputUtils.readIntColored("\n▸ Введите ID голосования: ", ConsoleDesign.BLUE);

        String sortBy = InputUtils.readLineColored("▸ Сортировать по (name/votes): ", ConsoleDesign.BLUE);

        // Получение и вывод результатов
        List<VotingResult> results = cecService.getVotingResults(votingId, "candidate", sortBy);
        ConsoleDesign.printSubHeader("РЕЗУЛЬТАТЫ ГОЛОСОВАНИЯ");
        results.forEach(r -> System.out.printf(
                ConsoleDesign.GREEN + "▸ %-20s: %d голосов\n" + ConsoleDesign.RESET,
                r.getCandidateName(),
                r.getVotes()
        ));
    }

    private void createVoting() {
        ConsoleDesign.clearScreen();
        LocalDate endDate = LocalDate.parse(InputUtils.readLineColored(
                "▸ Введите дату окончания (гггг-мм-дд): ", ConsoleDesign.BLUE
        ));
        boolean success = cecService.createVoting(currentCECId, endDate);
        if (success) ConsoleDesign.printSuccessBox("Голосование создано!");
        else ConsoleDesign.printErrorBox("Ошибка создания!");
    }

    private void addCandidate() {
        ConsoleDesign.clearScreen();
        String login = InputUtils.readLineColored("▸ Логин кандидата: ", ConsoleDesign.BLUE);
        String password = InputUtils.readLineColored("▸ Пароль: ", ConsoleDesign.BLUE);
        String fullName = InputUtils.readLineColored("▸ ФИО: ", ConsoleDesign.BLUE);
        String info = InputUtils.readLineColored("▸ Информация о кандидате: ", ConsoleDesign.BLUE);

        boolean success = cecService.addCandidate(login, password, fullName, info, currentCECId);
        if (success) ConsoleDesign.printSuccessBox("Кандидат добавлен!");
        else ConsoleDesign.printErrorBox("Ошибка добавления!");
    }

    private void exportToPDF() {
        ConsoleDesign.clearScreen();

        // Получаем список всех голосований
        List<Voting> votings = cecService.getAllVotings(currentCECId);
        if (votings.isEmpty()) {
            ConsoleDesign.printErrorBox("Нет голосований!");
            return;
        }

        // Выводим список ID голосований
        ConsoleDesign.printSubHeader("ВСЕ ГОЛОСОВАНИЯ");
        votings.forEach(v -> System.out.printf(
                ConsoleDesign.CYAN + "ID: %d | Дата окончания: %s\n" + ConsoleDesign.RESET,
                v.getId(),
                v.getEndDate()
        ));

        String inputIds = InputUtils.readLineColored("▸ Введите ID голосований через запятую: ", ConsoleDesign.BLUE);
        List<Integer> votingIds = new ArrayList<>();

        try {
            for (String id : inputIds.split(",")) {
                votingIds.add(Integer.parseInt(id.trim()));
            }
        } catch (NumberFormatException e) {
            ConsoleDesign.printError("Неверный формат ID!");
            return;
        }

        // Запрос группировки
        ConsoleDesign.printSubHeader("ВАРИАНТЫ ГРУППИРОВКИ");
        System.out.println(ConsoleDesign.CYAN + "1. По кандидатам\n2. По голосованиям" + ConsoleDesign.RESET);
        String groupBy = InputUtils.readLineColored("▸ Выберите группировку: ", ConsoleDesign.BLUE);
        groupBy = groupBy.equals("1") ? "candidate" : "voting";

        // Запрос сортировки
        ConsoleDesign.printSubHeader("ВАРИАНТЫ СОРТИРОВКИ");
        System.out.println(ConsoleDesign.CYAN + "1. По имени\n2. По голосам" + ConsoleDesign.RESET);
        String sortBy = InputUtils.readLineColored("▸ Выберите сортировку: ", ConsoleDesign.BLUE);
        sortBy = sortBy.equals("1") ? "name" : "votes";

        // Запрос о сохранении в один файл (только для нескольких ID)
        boolean singleFile = false;
        if (votingIds.size() > 1) {
            singleFile = InputUtils.confirmColored("▸ Сохранить в один файл? (да/нет): ", ConsoleDesign.YELLOW);
        }

        // Запрос имени файла
        String fileName = InputUtils.readLineColored("▸ Введите имя файла (оставьте пустым для автоимени): ", ConsoleDesign.BLUE);
        if (fileName.isBlank()) {
            fileName = "results_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        }

        // Запрос пути
        String path = InputUtils.readLineColored("▸ Путь для сохранения: ", ConsoleDesign.BLUE);

        try {
            cecService.exportMultipleVotings(votingIds, path, singleFile, groupBy, sortBy, fileName);
            ConsoleDesign.printSuccessBox("Выгрузка завершена!");
        } catch (IOException e) {
            ConsoleDesign.printErrorBox("Ошибка: " + e.getMessage());
        }
    }
}