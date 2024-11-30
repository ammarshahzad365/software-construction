package expressivo;

import java.util.Objects;

/**
 * A Variable represents a variable in an expression.
 * Immutable.
 */
public class Variable implements Expression {
    private final String name;

    // Abstraction Function:
    //    Represents a variable with name `name`.
    // Representation Invariant:
    //    name is a nonempty string of letters.
    // Safety from Rep Exposure:
    //    All fields are private and final.

    public Variable(String name) {
        this.name = name;
        checkRep();
    }

    private void checkRep() {
        assert name != null && name.matches("[a-zA-Z]+");
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Variable)) return false;
        Variable that = (Variable) thatObject;
        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
