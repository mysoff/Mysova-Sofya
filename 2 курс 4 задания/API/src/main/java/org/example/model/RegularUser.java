package org.example.model;

public class RegularUser extends User {
    private String fullName;
    private String snils;

    public RegularUser(int id, String login, String password, String fullName, String snils) {
        super(id, login, password,"USER");
        this.fullName = fullName;
        this.snils = snils;
    }

    public String getFullName() { return fullName; }
    public String getSnils() { return snils; }
}