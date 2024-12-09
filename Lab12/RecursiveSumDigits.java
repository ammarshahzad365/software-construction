package com.amfk.lab12;

public class RecursiveSumDigits {

    /**
     * Computes the sum of the digits of a given non-negative integer.
     * The function recursively calculates the sum by extracting the last digit
     * and adding it to the sum of the remaining digits.
     * 
     * @param number the non-negative integer whose digits are to be summed
     * @return the sum of the digits of the given number
     * 
     * Preconditions:
     * - The input number is an integer, and will be converted to its absolute value.
     * - The method handles both positive and negative integers by converting negative numbers to positive.
     * 
     * Postconditions:
     * - Returns the sum of the digits of the number.
     * - The method works by repeatedly extracting the last digit (using modulo 10) and recursively summing the rest.
     * 
     * Edge Cases:
     * - If the input number is 0, the method returns 0 (base case).
     * - If the number is negative, it is first converted to positive before calculating the sum.
     * 
     * Example:
     * - sumOfDigits(12345) returns 15 (1 + 2 + 3 + 4 + 5).
     * - sumOfDigits(-12345) returns 15 (same as the positive case).
     */
    public static int sumOfDigits(int number) {
        number = Math.abs(number); // Convert negative numbers to positive
        if (number == 0) {
            return 0; // Base case: when the number is reduced to 0, return 0
        }
        return number % 10 + sumOfDigits(number / 10); // Recursive case
    }
}
