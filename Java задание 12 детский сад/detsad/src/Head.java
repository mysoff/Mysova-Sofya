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
            flag = option(choice);
        }
        exit();
    }

    private boolean option (int choice) {
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
                exit();
                flag = false;
                break;
            default:
                System.out.println("некорректный выбор, попробуйте снова");
        }
        return flag;
    }

    private void addGroup() {
        System.out.print("введите название группы: ");
        String groupName = scanner.nextLine();
        System.out.print("введите номер группы: ");
        int groupNumber = scanner.nextInt();
        nursery.addGroup(new Group(groupName, groupNumber));
    }

    private void removeGroup() {
        System.out.print("введите название группы для удаления: ");
        String groupName = scanner.nextLine();
        Group group = nursery.findGroupName(groupName);
        if (group != null) {
            nursery.removeGroup(group);
            System.out.println("группа удалена");
        } else {
            System.out.println("такой группы нет");
        }
    }

    private void addKidToGroup() {
        System.out.print("введите название группы: ");
        String groupName = scanner.nextLine();
        Group group = nursery.findGroupName(groupName);
        if (group != null) {
            System.out.print("введите ФИО ребенка: ");
            String kidFullName = scanner.nextLine();
            System.out.print("введите пол ребенка: ");
            String kidGender = scanner.nextLine();
            System.out.print("введите возраст ребенка: ");
            int kidAge = scanner.nextInt();
            group.addKid(new Kid(kidFullName, kidGender, kidAge));
            System.out.println("ребенок добавлен");
        } else {
            System.out.println("такой группы нет");
        }
    }

    private void removeKidFromGroup() {
        System.out.print("введите название группы: ");
        String groupName = scanner.nextLine();
        Group group = nursery.findGroupName(groupName);
        if (group != null) {
            System.out.print("введите ФИО ребенка для удаления: ");
            String kidFullNameToRemove = scanner.nextLine();
            Kid kidToRemove = findKidInGroup(group, kidFullNameToRemove);
            if (kidToRemove != null) {
                group.removeKid(kidToRemove);
                System.out.println("ребенок удален");
            } else {
                System.out.println("такого ребенка нет");
            }
        } else {
            System.out.println("такой группы нет");
        }
    }

    private Kid findKidInGroup(Group group, String fullName) {
        for (Kid kid : group.getKids()) {
            if (kid.getFullName().equalsIgnoreCase(fullName)) {
                return kid;
            }
        }
        return null;
    }

    private void exit() {
        System.out.println("выход из программы");
        scanner.close();
        System.exit(0);
    }
}

