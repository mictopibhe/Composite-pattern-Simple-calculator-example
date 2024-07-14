package pl.davidduke.composite;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pl.davidduke.component.Expression;

import java.util.ArrayList;
import java.util.List;

// Composite
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subtract implements Expression {
    final List<Expression> expressions = new ArrayList<Expression>();

    public void add (Expression subExpression) {
        expressions.add(subExpression);
    }
    @Override
    public double calculate() {
        double result;
        if (expressions.isEmpty()) {
            result = 0;
        } else {
            result = expressions.get(0).calculate();
            for (int i = 1; i < expressions.size(); i++) {
                result -= expressions.get(i).calculate();
            }
        }
        return result;
    }
}
