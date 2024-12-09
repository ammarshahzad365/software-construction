package com.amfk.lab12;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RecursiveMathParserTest {

    @Test
    void testEvaluateExpression_simpleAddition() {
        assertEquals(8, RecursiveMathParser.evaluateExpression("3+5"));
    }

    @Test
    void testEvaluateExpression_withPrecedence() {
        assertEquals(13, RecursiveMathParser.evaluateExpression("3+5*2"));
    }

    @Test
    void testEvaluateExpression_withParentheses() {
        assertEquals(16, RecursiveMathParser.evaluateExpression("(3+5)*2"));
    }

    @Test
    void testEvaluateExpression_floatingPoint() {
        assertEquals(7.5, RecursiveMathParser.evaluateExpression("3+4.5"));
    }

    @Test
    void testEvaluateExpression_invalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> RecursiveMathParser.evaluateExpression("3++5"));
    }

    @Test
    void testEvaluateExpression_divisionByZero() {
        assertThrows(ArithmeticException.class, () -> RecursiveMathParser.evaluateExpression("5/0"));
    }
}
