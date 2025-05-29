package org.example.controller;

import org.example.view.ConsoleView;
import org.example.model.EquationSolver;

public class EquationController {
    private final ConsoleView view;
    private final EquationSolver solver;

    public EquationController(ConsoleView view, EquationSolver solver) {
        this.view = view;
        this.solver = solver;
    }

    public void process(String equation) {
        try {
            double result = solver.solve(equation);
            view.displayResult(result);
        } catch (Exception e) {
//            e.printStackTrace();
            view.displayError(e.getMessage());
        }
    }
}