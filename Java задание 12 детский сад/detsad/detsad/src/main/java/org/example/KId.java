package org.example;

class Kid {
    private final String fullName;
    private final String gender;
    private final int age;

    public Kid(String fullName, String gender, int age) {
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return "ФИО: " + fullName + ", пол: " + gender + ", возраст: " + age;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}