package org.example.view;

import org.example.service.*;
import org.example.model.User;
import org.example.utils.ConsoleDesign;
import org.example.utils.InputUtils;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MainMenu {
    private final AuthService authService;
    private final UserService userService;
    private final AdminService adminService;
    private final CECService cecService;
    private final CandidateService candidateService;

    public MainMenu(
            AuthService authService,
            UserService userService,
            AdminService adminService,
            CECService cecService,
            CandidateService candidateService
    ) {
        this.authService = authService;
        this.userService = userService;
        this.adminService = adminService;
        this.cecService = cecService;
        this.candidateService = candidateService;
    }

    public void start() {
        ConsoleDesign.clearScreen();
        while (true) {
            ConsoleDesign.printHeader("СИСТЕМА ЭЛЕКТРОННОГО ГОЛОСОВАНИЯ");
            ConsoleDesign.printMenuBox(
                    "1. Вход",
                    "2. Регистрация",
                    "0. Выход"
            );
            String choice = InputUtils.readLineColored("▸ Выберите действие: ", ConsoleDesign.YELLOW);

            switch (choice) {
                case "1" -> handleLogin();
                case "2" -> handleRegistration();
                case "0" -> {
                    ConsoleDesign.printExitMessage();
                    return;
                }
                default -> ConsoleDesign.printError("Неверная команда!");
            }
        }
    }

    private void handleLogin() {
        ConsoleDesign.clearScreen();
        while (true) {
            ConsoleDesign.printSubHeader("АВТОРИЗАЦИЯ");
            String login = InputUtils.readLineColored("Логин: ", ConsoleDesign.CYAN);
            String password = InputUtils.readLineColored("Пароль: ", ConsoleDesign.CYAN);

            User user = authService.authenticate(login, password);
            if (user != null) {
                ConsoleDesign.printSuccess("Успешный вход!");
                showRoleMenu(user);
                return;
            }

            ConsoleDesign.printError("Ошибка аутентификации!");
            if (!InputUtils.confirmColored("Повторить попытку? (да/нет): ", ConsoleDesign.YELLOW)) break;
            ConsoleDesign.clearScreen();
        }
    }

    private void handleRegistration() {
        ConsoleDesign.clearScreen();
        ConsoleDesign.printSubHeader("РЕГИСТРАЦИЯ НОВОГО ПОЛЬЗОВАТЕЛЯ");
        try {
            String fullName = InputUtils.readLineColored("▸ ФИО: ", ConsoleDesign.BLUE);
            LocalDate dateOfBirth = LocalDate.parse(
                    InputUtils.readLineColored("▸ Дата рождения (гггг-мм-дд): ", ConsoleDesign.BLUE)
            );
            String snils = InputUtils.readLineColored("▸ СНИЛС: ", ConsoleDesign.BLUE);
            String login = InputUtils.readLineColored("▸ Логин: ", ConsoleDesign.BLUE);
            String password = InputUtils.readLineColored("▸ Пароль: ", ConsoleDesign.BLUE);

            boolean success = userService.registerUser(fullName, dateOfBirth, snils, login, password);
            if (success) {
                ConsoleDesign.printSuccessBox("Регистрация успешно завершена!");
            } else {
                ConsoleDesign.printErrorBox("Ошибка регистрации!");
            }
        } catch (DateTimeParseException e) {
            ConsoleDesign.printError("Неверный формат даты!");
        }
    }

    private void showRoleMenu(User user) {
        while (true) {
            // Показываем меню роли
            switch (user.getRole().toUpperCase()) {
                case "ADMIN" -> new AdminMenu(adminService, user.getId()).showMenu();
                case "CEC" -> new CECMenu(cecService, user.getId()).showMenu();
                case "CANDIDATE" -> new CandidateMenu(candidateService, user.getId()).showMenu();
                case "USER" -> new UserMenu(userService, user.getId()).showMenu();
                default -> ConsoleDesign.printError("Неизвестная роль!");
            }

            if (InputUtils.confirmColored("Выйти из системы? (да/нет): ", ConsoleDesign.YELLOW)) {
                ConsoleDesign.clearScreen();
                return; // Выход в главное меню
            } else {
                ConsoleDesign.clearScreen();
            }
        }
    }
}