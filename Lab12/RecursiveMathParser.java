package com.amfk.lab12;

public class RecursiveMathParser {

    /**
     * Evaluates a mathematical expression given as a string using recursion.
     * The expression may contain addition (+), subtraction (-), multiplication (*), and division (/).
     * 
     * @param expression the mathematical expression as a string (e.g., "3 + 5 * 2")
     * @return the result of the evaluated expression as a double
     * @throws ArithmeticException if division by zero is encountered
     * 
     * Preconditions:
     * - The input expression must be a valid mathematical expression with proper syntax.
     * - The expression should not contain any unsupported characters.
     * 
     * Postconditions:
     * - If the expression is valid, returns the computed result as a double.
     * - If the expression is invalid or division by zero occurs, an exception is thrown.
     */
    public static double evaluateExpression(String expression) {
        return parseExpression(expression.replaceAll("\\s+", ""), 0, expression.length() - 1);
    }

    /**
     * Parses and evaluates a sub-expression within the given range of the main expression.
     * Handles parenthesis, operator precedence, and recursively evaluates sub-expressions.
     * 
     * @param expression the mathematical expression as a string
     * @param start the starting index of the sub-expression
     * @param end the ending index of the sub-expression
     * @return the result of the evaluated sub-expression as a double
     * @throws ArithmeticException if division by zero is encountered
     * 
     * Preconditions:
     * - The sub-expression must be well-formed (valid numbers and operators).
     * - The indices `start` and `end` should be within the bounds of the string.
     * 
     * Postconditions:
     * - The result of the sub-expression is returned as a double.
     * - If the sub-expression contains invalid operations, an exception is thrown.
     */
    private static double parseExpression(String expression, int start, int end) {
        // Handle parentheses: recursively evaluate if the entire sub-expression is enclosed in parentheses
        if (expression.charAt(start) == '(' && expression.charAt(end) == ')') {
            return parseExpression(expression, start + 1, end - 1);
        }

        // Find the first operator by precedence (first +, - then * and /)
        int operatorIndex = findOperator(expression, start, end, "+-");
        if (operatorIndex == -1) {
            operatorIndex = findOperator(expression, start, end, "*/");
        }

        if (operatorIndex != -1) {
            char operator = expression.charAt(operatorIndex);
            double left = parseExpression(expression, start, operatorIndex - 1);
            double right = parseExpression(expression, operatorIndex + 1, end);

            switch (operator) {
                case '+':
                    return left + right;
                case '-':
                    return left - right;
                case '*':
                    return left * right;
                case '/':
                    if (right == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    return left / right;
            }
        }

        // If no operator found, parse the sub-expression as a number
        return Double.parseDouble(expression.substring(start, end + 1));
    }

    /**
     * Finds the index of the first operator in the expression within the given range.
     * The operator is chosen based on its precedence in the given `operators` string.
     * 
     * @param expression the mathematical expression as a string
     * @param start the starting index of the search range
     * @param end the ending index of the search range
     * @param operators a string containing the operators to search for (e.g., "+-", "/*")
     * @return the index of the first found operator, or -1 if no operator is found
     * 
     * Preconditions:
     * - The `operators` string should contain valid mathematical operators in order of precedence.
     * - `start` and `end` should be within the bounds of the string.
     * 
     * Postconditions:
     * - Returns the index of the first operator found in the range, or -1 if not found.
     */
    private static int findOperator(String expression, int start, int end, String operators) {
        int balance = 0; // Track the balance of parentheses
        for (int i = end; i >= start; i--) {
            char c = expression.charAt(i);
            if (c == ')') balance++;
            if (c == '(') balance--;
            if (balance == 0 && operators.indexOf(c) >= 0) {
                return i; // Return the operator found outside of parentheses
            }
        }
        return -1; // No operator found
    }
}
