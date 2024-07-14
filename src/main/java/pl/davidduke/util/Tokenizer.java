package pl.davidduke.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tokenizer {
    String expression;
    int pos;
    int length;

    Map<String, String> variables;

    public Tokenizer(String inputLine, Map<String, String> variables) {
        expression = inputLine.replace(" ", "").replace(",", ".");
        this.variables = variables;
        length = expression.length();
        pos = 0;
    }

    public List<String> tokenize() {
        List<String> tokens = new ArrayList<>();
        String regex = "^[a-zA-Z0-9\\s\\+\\-*/^.]+$";
        if (!expression.matches(regex)) {
            throw new IllegalArgumentException(
                    "The entered expression " + expression + " contains invalid characters!"
            );
        }
        while (pos < length) {
            char currentChar = expression.charAt(pos);
            if (isOperator(currentChar)) {
                if (isUnaryMinus()) {
                    tokens.add("-1");
                    tokens.add("*");
                } else {
                    tokens.add(String.valueOf(currentChar));
                }
                pos++;
            } else {
                tokens.add(extractOperand());
            }
        }
        return tokens;
    }

    private boolean isUnaryMinus() {
        String regex = "^[+*/^()-]+$";
        return pos == 0 || expression.substring(pos - 1, pos).matches(regex);
    }

    private String extractOperand() {
        StringBuilder sb = new StringBuilder();
        while (pos < length && !isOperator(expression.charAt(pos))) {
            sb.append(expression.charAt(pos++));
        }
        String operand = sb.toString();
        return variables.getOrDefault(operand, operand);
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
}
