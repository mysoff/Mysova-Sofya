package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
        return "full-name: " + fullName + ", gender: " + gender + ", age: " + age;
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