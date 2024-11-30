package expressivo;

import java.util.Objects;

/**
 * Multiplication represents the multiplication of two expressions.
 * Immutable.
 */
public class Multiplication implements Expression {
    private final Expression left, right;

    // Abstraction Function:
    //    Represents the multiplication `left * right`.
    // Representation Invariant:
    //    left and right are not null.
    // Safety from Rep Exposure:
    //    All fields are private and final, and references are immutable.

    public Multiplication(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        checkRep();
    }

    private void checkRep() {
        assert left != null && right != null;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Multiplication)) return false;
        Multiplication that = (Multiplication) thatObject;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
