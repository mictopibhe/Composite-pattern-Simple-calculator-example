package pl.davidduke.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import pl.davidduke.component.Expression;
import pl.davidduke.composite.*;
import pl.davidduke.node.Number;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ThreeBuilder {

    private ThreeBuilder() {
        // private constructor
    }

    public static Expression buildExpressionTree(List<String> tokens) {
        Deque<Expression> composites = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();
        for (String token : tokens) {
            if (isNumber(token)) {
                composites.push(new Number(Double.parseDouble(token)));
            } else {
                while (!operators.isEmpty()) {
                    if (priority(token) > priority(operators.peek())) {
                        break;
                    } else {
                        composites.push(createComposite(operators.pop(), composites.pop(), composites.pop()));
                    }
                }
                operators.push(token);
            }
        }
        while (!operators.isEmpty()) {
            composites.push(createComposite(operators.pop(), composites.pop(), composites.pop()));
        }
        return composites.pop();
    }

    private static Expression createComposite(String pop, Expression left, Expression right) {
        Expression composite;
        switch (pop) {
            case "+":
                composite = new Add();
                ((Add) composite).add(left);
                ((Add) composite).add(right);
                break;
            case "-":
                composite = new Subtract();
                ((Subtract) composite).add(left);
                ((Subtract) composite).add(right);
                break;
            case "*":
                composite = new Multiply();
                ((Multiply) composite).add(left);
                ((Multiply) composite).add(right);
                break;
            case "/":
                composite = new Divide();
                ((Divide) composite).add(left);
                ((Divide) composite).add(right);
                break;
            case "^":
                composite = new Squaring();
                ((Squaring) composite).add(left);
                ((Squaring) composite).add(right);
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + pop);
        }
        return composite;
    }

    private static int priority(String token) {
        switch (token) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid token: " + token);
        }
    }

    private static boolean isNumber(String token) {
        boolean result = true;
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }
}
