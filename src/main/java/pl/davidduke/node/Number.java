package pl.davidduke.node;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pl.davidduke.component.Expression;

// Node
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Number implements Expression {
    double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double calculate() {
        return value;
    }
}
