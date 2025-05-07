package org.example;

import org.example.service.*;
import org.example.view.*;

public class VotingSystem {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        UserService userService = new UserService();
        AdminService adminService = new AdminService();
        CECService cecService = new CECService();
        CandidateService candidateService = new CandidateService();

        new MainMenu(
                authService,
                userService,
                adminService,
                cecService,
                candidateService
        ).start();
    }
}