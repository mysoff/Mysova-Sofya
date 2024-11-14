package org.example;

import java.util.List;
import java.util.Scanner;

class Head {
    private final Scanner scanner;
    private final Nursery nursery;

    public Head() {
        scanner = new Scanner(System.in);
        nursery = new Nursery();
    }

    public void showHead() {
        boolean flag = true;
        while (flag) {
            System.out.println("1 добавить группу");
            System.out.println("2 удалить группу");
            System.out.println("3 добавить ребенка в группу");
            System.out.println("4 удалить ребенка из группы");
            System.out.println("5 вывести информацию по группам");
            System.out.println("6 выйти");
            System.out.print("выбор действия: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            flag = handleChoice(choice);
        }
        exit();
    }

    private boolean handleChoice(int choice) {
        boolean flag = true;
        switch (choice) {
            case 1:
                addGroup();
                break;
            case 2:
                removeGroup();
                break;
            case 3:
                addKidToGroup();
                break;
            case 4:
                removeKidFromGroup();
                break;
            case 5:
                nursery.printGroups();
                break;
            case 6:
                flag = false;
                break;
            default:
                System.out.println("Некорректный выбор, попробуйте снова.");
        }
        return flag;
    }

    private void addGroup() {
        System.out.print("Введите название группы: ");
        String groupName = scanner.nextLine();
        System.out.print("Введите номер группы: ");
        int groupNumber = scanner.nextInt();
        scanner.nextLine(); // Считываем оставшийся символ новой строки
        nursery.addGroup(new Group(groupName, groupNumber, nursery.getConnection()));
    }

    private void removeGroup() {
        System.out.print("Введите название группы: ");
        String groupName = scanner.nextLine();

        System.out.print("Введите номер группы: ");
        int groupNumber = scanner.nextInt();
        scanner.nextLine();
        Group group = nursery.findGroupByNumberAndName(groupNumber, groupName);
        if (group != null) {
            nursery.removeGroup(group);
            System.out.println("Группа удалена");
        } else {
            System.out.println("Такой группы нет");
        }
    }

    private void addKidToGroup() {
        System.out.print("Введите название группы: ");
        String groupName = scanner.nextLine();

        System.out.print("Введите номер группы: ");
        int groupNumber = scanner.nextInt();
        scanner.nextLine();
        Group group = nursery.findGroupByNumberAndName(groupNumber, groupName);
        if (group != null) {
            System.out.print("Введите ФИО ребенка: ");
            String childFullName = scanner.nextLine();
            System.out.print("Введите пол ребенка: ");
            String childGender = scanner.nextLine();
            System.out.print("Введите возраст ребенка: ");
            int childAge = scanner.nextInt();
            scanner.nextLine(); // Считываем оставшийся символ новой строки

            group.addKid(new Kid(childFullName, childGender, childAge));
            System.out.println("Ребенок добавлен");
        } else {
            System.out.println("Такой группы нет");
        }
    }

    private void removeKidFromGroup() {
        System.out.print("Введите название группы: ");
        String groupName = scanner.nextLine();

        System.out.print("Введите номер группы: ");
        int groupNumber = scanner.nextInt();
        scanner.nextLine();
        Group group = nursery.findGroupByNumberAndName(groupNumber, groupName);
        if (group != null) {
            System.out.print("Введите ФИО ребенка для удаления: ");
            String childFullNameToRemove = scanner.nextLine();
            Kid kidToRemove = findKidInGroup(group, childFullNameToRemove);
            if (kidToRemove != null) {
                group.removeKid(kidToRemove);
                System.out.println("Ребенок удален");
            } else {
                System.out.println("Такого ребенка нет");
            }
        } else {
            System.out.println("Такой группы нет");
        }
    }

    private Kid findKidInGroup(Group group, String fullName) {
        List<Kid> kids = group.getKids();
        for (Kid kid : kids) {
            if (kid.getFullName().equalsIgnoreCase(fullName)) {
                return kid;
            }
        }
        return null;
    }

    private void exit() {
        System.out.println("Выход из программы.");
        scanner.close();
    }
}