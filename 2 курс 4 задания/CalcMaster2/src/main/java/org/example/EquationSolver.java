package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class EquationSolver {

    public double solve(String equation) {
        validateParentheses(equation);
        List<String> tokens = tokenize(equation);

        // Подсчёт количества чисел
        int numberCount = 0;
        for (String token : tokens) {
            if (isNumber(token)) {
                numberCount++;
            }
        }

        if (numberCount > 15) {
            throw new IllegalArgumentException("Уравнение содержит более 15 чисел");
        }

        List<String> postfix = shuntingYard(tokens);
        return evaluatePostfix(postfix);
    }

    // Токенизация с явной обработкой функций и скобок
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
            } else if (c == '-' && (i == 0 || isOperatorOrBracket(equation.charAt(i - 1)))) {
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                i++;
                while (i < length && (Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.')) {
                    sb.append(equation.charAt(i));
                    i++;
                }
                tokens.add(sb.toString());
            } else if (c == 'l' && i + 3 < length && equation.startsWith("log(", i)) {
                tokens.add("log");
                tokens.add("("); // Явно добавляем "("
                i += 4; // Пропускаем "log("
            } else if (c == 'e' && i + 4 < length && equation.startsWith("exp(", i)) {
                tokens.add("exp");
                tokens.add("("); // Явно добавляем "("
                i += 4; // Пропускаем "exp("
            } else if (c == '!' || c == '^') {
                tokens.add(String.valueOf(c));
                i++;
            } else if (c == '*' && i + 1 < length && equation.charAt(i + 1) == '*') {
                tokens.add("**");
                i += 2;
            } else if (c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
                i++;
            } else if (isOperator(c)) {
                tokens.add(String.valueOf(c));
                i++;
            } else if (Character.isWhitespace(c)) {
                i++;
            } else {
                throw new IllegalArgumentException("Неверный символ: " + c);
            }
        }
        return tokens;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean isOperatorOrBracket(char c) {
        return isOperator(c) || c == '(' || c == ')';
    }

    // Алгоритм Shunting Yard с обработкой функций
    private List<String> shuntingYard(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        HashMap<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("^", 3);
        precedence.put("**", 3);
        precedence.put("!", 4);
        precedence.put("log", 5);
        precedence.put("exp", 5);

        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Несбалансированные скобки");
                }
                stack.pop(); // Удаляем "("
                // Добавляем функцию, если она есть в стеке
                if (!stack.isEmpty() && (stack.peek().equals("log") || stack.peek().equals("exp"))) {
                    output.add(stack.pop());
                }
            } else if (precedence.containsKey(token)) {
                while (!stack.isEmpty() &&
                        precedence.get(token) <= precedence.getOrDefault(stack.peek(), 0) &&
                        !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else {
                throw new IllegalArgumentException("Неизвестный токен: " + token);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) {
                throw new IllegalArgumentException("Несбалансированные скобки");
            }
            output.add(stack.pop());
        }

        return output;
    }

    // Вычисление постфиксной записи с проверкой аргументов
    private double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                switch (token) {
                    case "+":
                        stack.push(stack.pop() + stack.pop());
                        break;
                    case "-":
                        double b = stack.pop();
                        double a = stack.pop();
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(stack.pop() * stack.pop());
                        break;
                    case "/":
                        double denominator = stack.pop();
                        if (denominator == 0) throw new ArithmeticException("Деление на ноль");
                        stack.push(stack.pop() / denominator);
                        break;
                    case "**":
                    case "^":
                        double exponent = stack.pop();
                        double base = stack.pop();
                        stack.push(Math.pow(base, exponent));
                        break;
                    case "!":
                        double num = stack.pop();
                        if (num < 0 || num != (int) num) {
                            throw new IllegalArgumentException("Факториал определен для целых ≥ 0");
                        }
                        stack.push(factorial((int) num));
                        break;
                    case "log":
                        if (stack.isEmpty()) throw new IllegalArgumentException("Нет аргумента для log");
                        double logArg = stack.pop();
                        if (logArg <= 0) throw new IllegalArgumentException("Логарифм неположительного числа");
                        stack.push(Math.log(logArg) / Math.log(2));
                        break;
                    case "exp":
                        if (stack.isEmpty()) throw new IllegalArgumentException("Нет аргумента для exp");
                        stack.push(Math.exp(stack.pop()));
                        break;
                    default:
                        throw new IllegalArgumentException("Неизвестный оператор: " + token);
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        return stack.pop();
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double factorial(int n) {
        if (n == 0) return 1;
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private void validateParentheses(String equation) {
        int balance = 0;
        for (char c : equation.toCharArray()) {
            if (c == '(') balance++;
            else if (c == ')') balance--;
            if (balance < 0) throw new IllegalArgumentException("Несбалансированные скобки");
        }
        if (balance != 0) throw new IllegalArgumentException("Несбалансированные скобки");
    }
}