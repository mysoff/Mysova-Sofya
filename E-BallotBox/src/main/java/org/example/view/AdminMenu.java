package org.example.view;

import org.example.service.AdminService;
import org.example.utils.ConsoleDesign;
import org.example.utils.InputUtils;

public class AdminMenu {
    private final AdminService adminService;
    private final int currentAdminId;

    public AdminMenu(AdminService adminService, int adminId) {
        this.adminService = adminService;
        this.currentAdminId = adminId;
    }

    public void showMenu() {
        ConsoleDesign.clearScreen();
        while (true) {
            ConsoleDesign.printHeader("МЕНЮ АДМИНИСТРАТОРА");
            ConsoleDesign.printMenuBox(
                    "1. Просмотр/удаление пользователей",
                    "2. Просмотр/удаление ЦИК",
                    "3. Создать ЦИК",
                    "4. Просмотр/удаление кандидатов",
                    "0. Выход"
            );
            String choice = InputUtils.readLineColored("▸ Выберите действие: ", ConsoleDesign.YELLOW);

            switch (choice) {
                case "1" -> handleUsers();
                case "2" -> handleCECs();
                case "3" -> createCEC();
                case "4" -> handleCandidates();
                case "0" -> {
                    ConsoleDesign.clearScreen();
                    return;
                }
                default -> ConsoleDesign.printError("Неверная команда!");
            }
        }
    }

    private void handleUsers() {
        ConsoleDesign.clearScreen();
        ConsoleDesign.printSubHeader("СПИСОК ПОЛЬЗОВАТЕЛЕЙ");
        adminService.getAllUsers().forEach(regularUser ->
                System.out.printf(
                        ConsoleDesign.CYAN + "%-5d %-15s %-15s %-15s\n" + ConsoleDesign.RESET,
                        regularUser.getId(),
                        regularUser.getLogin(),
                        regularUser.getFullName(),
                        regularUser.getSnils()
                )
        );
        String login = InputUtils.readLineColored("\n▸ Логин для удаления (0 - отмена): ", ConsoleDesign.BLUE);
        if (!login.equals("0")) {
            boolean success = adminService.deleteUser(login);
            if (success) ConsoleDesign.printSuccessBox("Пользователь удален!");
            else ConsoleDesign.printErrorBox("Пользователь не найден!");
        }
    }

    private void handleCECs() {
        ConsoleDesign.clearScreen();
        ConsoleDesign.printSubHeader("СПИСОК ЦИК");
        adminService.getAllCECs().forEach(cec ->
                System.out.printf(
                        ConsoleDesign.CYAN + "%-20s (Создатель: Admin#%d)\n" + ConsoleDesign.RESET,
                        cec.getLogin(),
                        cec.getCreatedByAdminId()
                )
        );
        String login = InputUtils.readLineColored("\n▸ Логин ЦИК для удаления (0 - отмена): ", ConsoleDesign.BLUE);
        if (!login.equals("0")) {
            boolean success = adminService.deleteCEC(login);
            if (success) ConsoleDesign.printSuccessBox("ЦИК удален!");
            else ConsoleDesign.printErrorBox("ЦИК не найден!");
        }
    }

    private void createCEC() {
        ConsoleDesign.clearScreen();
        String login = InputUtils.readLineColored("▸ Логин ЦИК: ", ConsoleDesign.BLUE);
        String password = InputUtils.readLineColored("▸ Пароль ЦИК: ", ConsoleDesign.BLUE);
        boolean success = adminService.createCEC(login, password, currentAdminId);
        if (success) ConsoleDesign.printSuccessBox("ЦИК создан!");
        else ConsoleDesign.printErrorBox("Логин занят!");
    }

    private void handleCandidates() {
        ConsoleDesign.clearScreen();
        ConsoleDesign.printSubHeader("СПИСОК КАНДИДАТОВ");
        adminService.getCandidates().forEach(candidate ->
                System.out.printf(
                        ConsoleDesign.CYAN + "%-5d %-20s %-30s\n" + ConsoleDesign.RESET,
                        candidate.getId(),
                        candidate.getLogin(),
                        candidate.getInfo()
                )
        );
        String login = InputUtils.readLineColored("\n▸ Логин кандидата для удаления (0 - отмена): ", ConsoleDesign.BLUE);
        if (!login.equals("0")) {
            boolean success = adminService.deleteCandidate(login);
            if (success) ConsoleDesign.printSuccessBox("Кандидат удален!");
            else ConsoleDesign.printErrorBox("Кандидат не найден!");
        }
    }
}