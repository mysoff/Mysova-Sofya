package org.example.model;

public class CEC extends User {
    private int createdByAdminId;

    public CEC(int id, String login, String password, int createdByAdminId) {
        super(id, login, password, "CEC");
        this.createdByAdminId = createdByAdminId;
    }

    public int getCreatedByAdminId() {
        return createdByAdminId;
    }
}