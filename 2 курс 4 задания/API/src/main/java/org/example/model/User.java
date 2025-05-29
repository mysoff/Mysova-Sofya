package org.example.model;

public abstract class User {
    private int id;
    private String login;
    private String password;
    private String role;
//    private String fullName;

    public User(int id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
//        this.fullName = fullName;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
//    public String getFullName() { return fullName; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

}