import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Kindergarten kindergarten = new Kindergarten();

        while (true) {
            System.out.println("1. Добавить группу");
            System.out.println("2. Удалить группу");
            System.out.println("3. Добавить ребенка в группу");
            System.out.println("4. Удалить ребенка из группы");
            System.out.println("5. Вывести информацию по группам");
            System.out.println("6. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Чистим буфер

            switch (choice) {
                case 1:
                    System.out.print("Введите название группы: ");
                    String groupName = scanner.nextLine();
                    System.out.print("Введите номер группы: ");
                    int groupNumber = scanner.nextInt();
                    kindergarten.addGroup(new Group(groupName, groupNumber));
                    break;

                case 2:
                    System.out.print("Введите название группы для удаления: ");
                    String removeGroupName = scanner.nextLine();
                    Group groupToRemove = kindergarten.findGroupByName(removeGroupName);
                    if (groupToRemove != null) {
                        kindergarten.removeGroup(groupToRemove);
                        System.out.println("Группа удалена.");
                    } else {
                        System.out.println("Группа не найдена.");
                    }
                    break;

                case 3:
                    System.out.print("Введите название группы: ");
                    String addChildGroupName = scanner.nextLine();
                    Group groupToAddChild = kindergarten.findGroupByName(addChildGroupName);
                    if (groupToAddChild != null) {
                        System.out.print("Введите ФИО ребенка: ");
                        String childFullName = scanner.nextLine();
                        System.out.print("Введите пол ребенка: ");
                        String childGender = scanner.nextLine();
                        System.out.print("Введите возраст ребенка: ");
                        int childAge = scanner.nextInt();
                        groupToAddChild.addChild(new Child(childFullName, childGender, childAge));
                        System.out.println("Ребенок добавлен.");
                    } else {
                        System.out.println("Группа не найдена.");
                    }
                    break;

                case 4:
                    System.out.print("Введите название группы: ");
                    String removeChildGroupName = scanner.nextLine();
                    Group groupToRemoveChild = kindergarten.findGroupByName(removeChildGroupName);
                    if (groupToRemoveChild != null) {
                        System.out.print("Введите ФИО ребенка для удаления: ");
                        String childFullNameToRemove = scanner.nextLine();
                        Child childToRemove = null;
                        for (Child child : groupToRemoveChild.getChildren()) {
                            if (child.toString().contains(childFullNameToRemove)) {
                                childToRemove = child;
                                break;
                            }
                        }
                        if (childToRemove != null) {
                            groupToRemoveChild.removeChild(childToRemove);
                            System.out.println("Ребенок удален.");
                        } else {
                            System.out.println("Ребенок не найден.");
                        }
                    } else {
                        System.out.println("Группа не найдена.");
                    }
                    break;

                case 5:
                    kindergarten.printGroups();
                    break;

                case 6:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Некорректный выбор, попробуйте снова.");
            }
        }
    }
}
