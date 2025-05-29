package org.example.utils;

import java.time.LocalDate;
import java.util.Scanner;
import org.example.view.*;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    // Чтение целого числа с валидацией
    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число!");
            }
        }
    }

    // Чтение строки с валидацией пустого ввода
    public static String readLine(String prompt) {
        System.out.print(prompt);
        String input;
        do {
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Поле не может быть пустым!");
            }
        } while (input.isEmpty());
        return input;
    }

    // Чтение даты с валидацией формата
    public static LocalDate readDate(String prompt) {
        while (true) {
            String dateStr = readLine(prompt);
            if (DateUtils.isValidFormat(dateStr)) {
                return DateUtils.parseDate(dateStr);
            }
            System.out.println("Неверный формат даты! Используйте dd.MM.yyyy");
        }
    }

    // Подтверждение действия (Да/Нет)
    public static boolean confirm(String prompt) {
        System.out.print(prompt + " (Да/Нет): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.startsWith("д") || input.startsWith("y");
    }

    public static String readLineColored(String prompt, String color) {
        System.out.print(color + prompt + ConsoleDesign.RESET);
        return scanner.nextLine();
    }

    public static boolean confirmColored(String prompt, String color) {
        System.out.print(color + prompt + ConsoleDesign.RESET);
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("да") || input.equals("yes") || input.equals("y");
    }

    public static int readIntColored(String prompt, String color) {
        System.out.print(color + prompt + ConsoleDesign.RESET);
        return Integer.parseInt(scanner.nextLine());
    }
}