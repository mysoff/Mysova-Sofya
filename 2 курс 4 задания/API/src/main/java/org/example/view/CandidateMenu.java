package org.example.view;

import org.example.model.*;
import org.example.service.*;
import org.example.utils.ConsoleDesign;
import org.example.utils.InputUtils;
import java.util.List;

public class CandidateMenu {
    private final CandidateService candidateService;
    private final int candidateId;

    public CandidateMenu(CandidateService candidateService, int candidateId) {
        this.candidateService = candidateService;
        this.candidateId = candidateId;
    }

    public void showMenu() {
        ConsoleDesign.clearScreen();
        while (true) {
            ConsoleDesign.printHeader("МЕНЮ КАНДИДАТА");
            ConsoleDesign.printMenuBox(
                    "1. Заполнить данные",
                    "2. Результаты прошлых голосований",
                    "3. Список участий в голосованиях",
                    "0. Выход"
            );
            String choice = InputUtils.readLineColored("▸ Выберите действие: ", ConsoleDesign.YELLOW);

            switch (choice) {
                case "1" -> updateProfile();
                case "2" -> showPreviousResults();
                case "3" -> showParticipatedVotings();
                case "0" -> {
                    ConsoleDesign.clearScreen();
                    return;
                }
                default -> ConsoleDesign.printError("Неверная команда!");
            }
        }
    }

    private void updateProfile() {
        ConsoleDesign.clearScreen();
        String newInfo = InputUtils.readLineColored("▸ Введите новую информацию о себе: ", ConsoleDesign.BLUE);
        boolean success = candidateService.updateCandidateInfo(candidateId, newInfo);
        if (success) ConsoleDesign.printSuccessBox("Данные обновлены!");
        else ConsoleDesign.printErrorBox("Ошибка обновления!");
    }

    private void showPreviousResults() {
        ConsoleDesign.clearScreen();
        List<VotingResult> results = candidateService.getPreviousResults(candidateId);
        ConsoleDesign.printSubHeader("РЕЗУЛЬТАТЫ ГОЛОСОВАНИЙ");
        results.forEach(r -> System.out.printf(
                ConsoleDesign.CYAN + "▸ %-20s: %d голосов\n" + ConsoleDesign.RESET,
                r.getCandidateName(),
                r.getVotes()
        ));
    }

    private void showParticipatedVotings() {
        ConsoleDesign.clearScreen();
        List<String> votings = candidateService.getParticipatedVotings(candidateId);
        ConsoleDesign.printSubHeader("ИСТОРИЯ УЧАСТИЯ");
        votings.forEach(v -> System.out.println(ConsoleDesign.GREEN + "▸ " + v + ConsoleDesign.RESET));
    }
}