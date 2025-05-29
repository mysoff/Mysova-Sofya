package org.example.view;

import org.example.service.UserService;
import org.example.utils.ConsoleDesign;
import org.example.utils.InputUtils;
import org.example.model.*;
import java.util.List;

public class UserMenu {
    private final UserService userService;
    private final int currentUserId;

    public UserMenu(UserService userService, int userId) {
        this.userService = userService;
        this.currentUserId = userId;
    }

    public void showMenu() {
        ConsoleDesign.clearScreen();
        while (true) {
            ConsoleDesign.printHeader("МЕНЮ ПОЛЬЗОВАТЕЛЯ");
            ConsoleDesign.printMenuBox(
                    "1. Проголосовать",
                    "2. Список кандидатов",
                    "3. История голосований",
                    "0. Выход"
            );
            String choice = InputUtils.readLineColored("▸ Выберите действие: ", ConsoleDesign.YELLOW);

            switch (choice) {
                case "1" -> vote();
                case "2" -> showCandidates();
                case "3" -> showVotingHistory();
                case "0" -> {
                    ConsoleDesign.clearScreen();
                    return;
                }
                default -> ConsoleDesign.printError("Неверная команда!");
            }
        }
    }

    private void vote() {
        ConsoleDesign.clearScreen();

        // Получаем активные голосования
        List<Voting> activeVotings = userService.getActiveVotings();
        if (activeVotings.isEmpty()) {
            ConsoleDesign.printErrorBox("Нет активных голосований!");
            return;
        }

        // Выбор голосования
        ConsoleDesign.printSubHeader("АКТИВНЫЕ ГОЛОСОВАНИЯ");
        activeVotings.forEach(v -> System.out.printf(
                ConsoleDesign.CYAN + "ID: %d | Дата окончания: %s\n" + ConsoleDesign.RESET,
                v.getId(),
                v.getEndDate()
        ));

        int votingId = InputUtils.readIntColored("\n▸ Введите ID голосования: ", ConsoleDesign.BLUE);

        // Получаем кандидатов для выбранного голосования
        List<Candidate> candidates = userService.getCandidatesForVoting(votingId);
        if (candidates.isEmpty()) {
            ConsoleDesign.printErrorBox("В этом голосовании нет кандидатов!");
            return;
        }

        // Выбор кандидата
        ConsoleDesign.printSubHeader("КАНДИДАТЫ В ГОЛОСОВАНИИ");
        candidates.forEach(c -> System.out.printf(
                ConsoleDesign.GREEN + "ID: %d | %s | %s\n" + ConsoleDesign.RESET,
                c.getId(),
                c.getFullName(),
                c.getInfo()
        ));

        int candidateId = InputUtils.readIntColored("\n▸ Введите ID кандидата: ", ConsoleDesign.BLUE);

        // Голосование
        boolean success = userService.vote(currentUserId, votingId, candidateId);
        if (success) ConsoleDesign.printSuccessBox("Голос учтен!");
        else ConsoleDesign.printErrorBox("Ошибка голосования!");
    }

    private void showCandidates() {
        ConsoleDesign.clearScreen();
        List<String> candidates = userService.getActiveCandidates();
        ConsoleDesign.printSubHeader("АКТИВНЫЕ КАНДИДАТЫ");
        if (candidates.isEmpty()) {
            ConsoleDesign.printError("Нет активных голосований!");
        } else {
            candidates.forEach(c -> System.out.println(ConsoleDesign.GREEN + "▸ " + c + ConsoleDesign.RESET));
        }
    }

    private void showVotingHistory() {
        ConsoleDesign.clearScreen();
        List<String> history = userService.getVotingHistory(currentUserId);
        ConsoleDesign.printSubHeader("ИСТОРИЯ ГОЛОСОВАНИЙ");
        history.forEach(h -> System.out.println(ConsoleDesign.CYAN + "▸ " + h + ConsoleDesign.RESET));
    }
}