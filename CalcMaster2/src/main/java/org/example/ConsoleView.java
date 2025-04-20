package org.example.view;

import java.text.DecimalFormat;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);
    private final DecimalFormat df = new DecimalFormat("#,###.##########");

    public String getEquation() {
        System.out.print("\n> Ввод: ");
        return scanner.nextLine().trim();
    }

    public String getExitCommand() {
        System.out.print("\n> Введите 'exit' для выхода или любой текст для продолжения: ");
        return scanner.nextLine();
    }

    public void displayResult(double result) {
        System.out.println("\nРезультат: " + df.format(result));
    }

    public void displayError(String message) {
        System.out.println("\nОшибка: " + message);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}