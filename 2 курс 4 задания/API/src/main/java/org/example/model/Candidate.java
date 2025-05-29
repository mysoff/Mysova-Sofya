package org.example.model;
public class Candidate extends User {
    private String info;
    private String fullName;

    public Candidate(int id, String login, String password, String info, String fullName) {
        super(id, login, password, "CANDIDATE");
        this.info = info;
        this.fullName = fullName;
    }

    public String getInfo() { return info; }
    public String getFullName() { return fullName; }
    public void setInfo(String info) { this.info = info; }
}