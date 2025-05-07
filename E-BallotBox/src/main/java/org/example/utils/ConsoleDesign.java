package org.example.utils;

public class ConsoleDesign {
    // ANSI цветовые коды
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printHeader(String title) {
        System.out.println(PURPLE);
        System.out.println("╔══════════════════════════════════════════════════╗");
        String centered = centerStringExact(title, 50);
        System.out.println("+" + centered + "+"); // 50 + 4 пробела = 54 символа
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    public static void printSubHeader(String title) {
        System.out.println(CYAN);
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("│ " + String.format("%-48s", title) + " │");
        System.out.println("└──────────────────────────────────────────────────┘");
        System.out.println(RESET);
    }

    public static void printMenuBox(String... items) {
        System.out.println(YELLOW);
        System.out.println("╔══════════════════════════════════════════════════╗");
        for (String item : items) {
            System.out.println("║  " + String.format("%-48s", item) + "║");
        }
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(GREEN + "[✓] " + message + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + "[✗] " + message + RESET);
    }

    public static void printSuccessBox(String message) {
        System.out.println(GREEN);
        System.out.println("╔══════════════════════════════════════════════════╗");
        String centered = centerStringExact(message, 48);
        System.out.println("OK" + centered + "ОК");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    private static String centerString(String text, int width) {
        int padding = (width - text.length()) / 2;
        return String.format("%" + (padding + text.length()) + "s", text);
    }

    public static void printExitMessage() {
        System.out.println(RED);
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║          ПРОГРАММА ЗАВЕРШЕНА.    чао-како!       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    public static void printErrorBox(String message) {
        System.out.println(RED);
        System.out.println("╔══════════════════════════════════════════════════╗");
        String centered = centerStringExact(message, 50);
        System.out.println("!" + centered + "!");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    private static String centerStringExact(String text, int width) {
        if (text.length() > width) {
            return text.substring(0, width); // Обрезаем, если текст длиннее
        }

        int totalPadding = width - text.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return String.format(
                "%" + leftPadding + "s%s%" + rightPadding + "s",
                "", text, ""
        );
    }
}