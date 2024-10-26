import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Класс для представления ребенка
class Child {
    private String fullName;
    private String gender;
    private int age;

    public Child(String fullName, String gender, int age) {
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return "ФИО: " + fullName + ", Пол: " + gender + ", Возраст: " + age;
    }
}