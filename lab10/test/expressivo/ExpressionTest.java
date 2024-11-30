package expressivo;

import static org.junit.Assert.*;



import org.junit.Test;

public class ExpressionTest {

  
    // Partition for addExpr
    @Test
    public void testAddExprEmpty() {
        Expression expr = new Number(0);
        Expression result = new Addition(expr, new Number(0));
        assertEquals("(0.0 + 0.0)", result.toString());
    }

    @Test
    public void testAddExprMultipleVariables() {
        Expression expr = new Addition(new Variable("x"), new Variable("y"));
        assertEquals("(x + y)", expr.toString());
    }

    @Test
    public void testAddExprSubset() {
        Expression subset = new Variable("x");
        Expression expr = new Addition(subset, new Variable("y"));
        assertTrue(expr.toString().contains(subset.toString()));
    }

    @Test
    public void testAddExprEquals() {
        Expression expr1 = new Addition(new Variable("x"), new Variable("y"));
        Expression expr2 = new Addition(new Variable("x"), new Variable("y"));
        assertEquals(expr1, expr2);
    }

    // Partition for multiplyExpr
    @Test
    public void testMultiplyExprEmpty() {
        Expression expr = new Number(0);
        Expression result = new Multiplication(expr, new Number(0));
        assertEquals("(0.0 * 0.0)", result.toString());
    }

    @Test
    public void testMultiplyExprIdentity() {
        Expression expr = new Multiplication(new Number(1), new Variable("x"));
        assertEquals("(1.0 * x)", expr.toString());
    }

    @Test
    public void testMultiplyExprMultipleVariables() {
        Expression expr = new Multiplication(new Variable("x"), new Variable("y"));
        assertEquals("(x * y)", expr.toString());
    }

    @Test
    public void testMultiplyExprSubset() {
        Expression subset = new Variable("x");
        Expression expr = new Multiplication(subset, new Variable("y"));
        assertTrue(expr.toString().contains(subset.toString()));
    }

    @Test
    public void testMultiplyExprEquals() {
        Expression expr1 = new Multiplication(new Variable("x"), new Variable("y"));
        Expression expr2 = new Multiplication(new Variable("x"), new Variable("y"));
        assertEquals(expr1, expr2);
    }




    // Partition for toString
    @Test
    public void testToStringEmptyExpression() {
        Expression expr = new Number(0);
        assertEquals("0.0", expr.toString());
    }

    @Test
    public void testToStringMultipleVariables() {
        Expression expr = new Addition(new Variable("x"), new Variable("y"));
        assertEquals("(x + y)", expr.toString());
    }

    // Partition for equals
    @Test
    public void testEqualsReflexive() {
        Expression expr = new Variable("x");
        assertEquals(expr, expr);
    }

    @Test
    public void testEqualsSymmetric() {
        Expression expr1 = new Variable("x");
        Expression expr2 = new Variable("x");
        assertEquals(expr1, expr2);
        assertEquals(expr2, expr1);
    }

    @Test
    public void testEqualsTransitive() {
        Expression expr1 = new Variable("x");
        Expression expr2 = new Variable("x");
        Expression expr3 = new Variable("x");
        assertEquals(expr1, expr2);
        assertEquals(expr2, expr3);
        assertEquals(expr1, expr3);
    }

    @Test
    public void testEqualsNumbersCorrectToFiveDecimals() {
        Expression expr1 = new Number(1.12345);
        Expression expr2 = new Number(1.12345);
        assertEquals(expr1, expr2);
    }

    // Partition for hashCode
    @Test
    public void testHashCodeEquality() {
        Expression expr1 = new Addition(new Variable("x"), new Number(5));
        Expression expr2 = new Addition(new Variable("x"), new Number(5));
        assertEquals(expr1.hashCode(), expr2.hashCode());
    }
}
