package org.example;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        displayMenu();
        int choice = scanner.nextInt();

        if (choice == 1) {
            Head head = new Head();
            head.showHead();
        } else if (choice == 2) {
            SwingUtilities.invokeLater(NurseryGUI::new);
        } else {
            System.out.println("Неверный выбор. Пожалуйста, введите 1 или 2.");
        }
    }

    private static void displayMenu() {
        System.out.println("Выберите вариант запуска:");
        System.out.println("1. Консольный");
        System.out.println("2. Графический");
        System.out.print("Введите 1 или 2: ");
    }
}