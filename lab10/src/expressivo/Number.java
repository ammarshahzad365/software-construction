package expressivo;

import java.util.Objects;

/**
 * A Number represents a constant numeric value in an expression.
 * Immutable.
 */
public class Number implements Expression {
    private final double value;

    // Abstraction Function:
    //    Represents a numeric constant `value`.
    // Representation Invariant:
    //    true (any double value is valid)
    // Safety from Rep Exposure:
    //    All fields are private and final.

    public Number(double value) {
        this.value = value;
        checkRep();
    }

    private void checkRep() {
        // No specific rep invariant for Number.
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Number)) return false;
        Number that = (Number) thatObject;
        return Double.compare(this.value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
