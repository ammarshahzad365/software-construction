package com.amfk.lab12;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RecursiveSumDigitsTest {

    @Test
    void testSumOfDigits_positiveNumber() {
        assertEquals(15, RecursiveSumDigits.sumOfDigits(12345));
    }

    @Test
    void testSumOfDigits_zero() {
        assertEquals(0, RecursiveSumDigits.sumOfDigits(0));
    }

    @Test
    void testSumOfDigits_largeNumber() {
        assertEquals(45, RecursiveSumDigits.sumOfDigits(987654321));
    }

    @Test
    void testSumOfDigits_negativeNumber() {
        assertEquals(15, RecursiveSumDigits.sumOfDigits(-12345));
    }

    @Test
    void testSumOfDigits_singleDigit() {
        assertEquals(7, RecursiveSumDigits.sumOfDigits(7)); // Single-digit number
    }

    @Test
    void testSumOfDigits_largeNegativeNumber() {
        assertEquals(45, RecursiveSumDigits.sumOfDigits(-987654321)); // Large negative number
    }
}
