package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class EquationSolver {

    public double solve(String equation) {
        List<String> tokens = tokenize(equation);
        List<String> postfix = shuntingYard(tokens);
        return evaluatePostfix(postfix);
    }

    private List<String> tokenize(String equation) {
        List<String> tokens = new ArrayList<>();
        int i = 0;
        int length = equation.length();
        while (i < length) {
            char c = equation.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < length && (Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.')) {
                    sb.append(equation.charAt(i));
                    i++;
                }
                tokens.add(sb.toString());
            } else if (c == '-' && (i == 0 || isOperator(equation.charAt(i - 1)) || equation.charAt(i - 1) == '(')) {
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                i++;
                while (i < length && (Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.')) {
                    sb.append(equation.charAt(i));
                    i++;
                }
                tokens.add(sb.toString());
            } else if (isOperator(c) || c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
                i++;
            } else if (Character.isWhitespace(c)) {
                i++;
            } else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }
        return tokens;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private List<String> shuntingYard(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        HashMap<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);

        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && !stack.peek().equals("(") && precedence.get(token) <= precedence.get(stack.peek())) {
                    output.add(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Недостаточно операндов для операции " + token);
                }
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        if (b == 0) throw new ArithmeticException("Деление на ноль");
                        stack.push(a / b);
                        break;
                    default:
                        throw new IllegalArgumentException("Неизвестный оператор: " + token);
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Недопустимое выражение");
        }

        return stack.pop();
    }
}