package org.example.model;

public class Admin extends User {
    private String fullName;
    public Admin(int id, String login, String password, String fullName) {
        super(id, login, password,"ADMIN");
        this.fullName = fullName;
    }

    public String getFullName() { return fullName; }
}