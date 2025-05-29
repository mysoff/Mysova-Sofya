package org.example;

import org.example.controller.EquationController;
import org.example.model.EquationSolver;
import org.example.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        displayMenu();
        ConsoleView view = new ConsoleView();
        EquationSolver solver = new EquationSolver();
        EquationController controller = new EquationController(view, solver);

        boolean isRunning = true;
        while (isRunning) {
            String input = view.getEquation(); // Получаем ввод пользователя
            if (isExitCommand(input)) {
                isRunning = false; // Выход из цикла
            } else if (isHelpCommand(input)) {
                displayMenu(); // Показываем меню
            } else {
                controller.process(input); // Вычисляем уравнение
            }
        }
        view.displayMessage("Программа завершена.");
    }

    private static boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("exit");
    }

    private static boolean isHelpCommand(String input) {
        return input.equalsIgnoreCase("help");
    }

    private static void displayMenu() {
        String header = "⚡ CalcMaster ";
        String[] operations = {
                "+  Сложение",
                "-  Вычитание",
                "*  Умножение",
                "/  Деление",
                "!  Факториал",
                "^, **  Степень",
                "log()  Лог. по основанию 2",
                "exp()  Экспонента"
        };

        // Фиксированная ширина рамки (30 символов)
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║" + centerText(header, 30) + "║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ Поддерживаемые операции:     ║");

        for (String op : operations) {
            System.out.println("║   " + String.format("%-27s", op) + "║");
        }

        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ Для выхода: 'exit'           ║");
        System.out.println("║ Для повтора меняю: 'help'    ║");
        System.out.println("╚══════════════════════════════╝");
    }

    // Метод для центрирования текста в заданной ширине
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }

}