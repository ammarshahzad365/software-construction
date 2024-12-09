package com.amfk.lab12;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class RecursiveBinarySearchTest {

    @Test
    void testBinarySearchRecursive_found() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(4, RecursiveBinarySearch.binarySearchRecursive(array, 5, 0, array.length - 1));
    }

    @Test
    void testBinarySearchRecursive_notFound() {
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(-1, RecursiveBinarySearch.binarySearchRecursive(array, 10, 0, array.length - 1));
    }

    @Test
    void testBinarySearchRecursive_emptyArray() {
        int[] array = {};
        assertEquals(-1, RecursiveBinarySearch.binarySearchRecursive(array, 5, 0, array.length - 1));
    }

    @Test
    void testBinarySearchRecursive_nullArray() {
        assertEquals(-1, RecursiveBinarySearch.binarySearchRecursive(null, 5, 0, 0));
    }

    @Test
    void testBinarySearchAllIndices() {
        int[] array = {1, 2, 2, 2, 3, 4};
        List<Integer> result = RecursiveBinarySearch.binarySearchAllIndices(array, 2, 0, array.length - 1);
        assertEquals(List.of(1, 2, 3), result);
    }
    
    @Test
    void testBinarySearchRecursive_strings() {
        String[] array = {"apple", "banana", "cherry", "date", "fig"};
        assertEquals(2, RecursiveBinarySearch.binarySearchRecursive(array, "cherry", 0, array.length - 1));
    }

    @Test
    void testBinarySearchRecursive_stringsNotFound() {
        String[] array = {"apple", "banana", "cherry"};
        assertEquals(-1, RecursiveBinarySearch.binarySearchRecursive(array, "grape", 0, array.length - 1));
    }

    @Test
    void testBinarySearchAllIndices_withDuplicates() {
        int[] array = {1, 2, 3, 3, 3, 4, 5};
        List<Integer> result = RecursiveBinarySearch.binarySearchAllIndices(array, 3, 0, array.length - 1);
        assertEquals(List.of(2, 3, 4), result); // Checks all occurrences of "3"
    }

    @Test
    void testBinarySearchRecursive_largeArrayEdgeValue() {
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1; // Fill with 1 to 1000
        }
        assertEquals(999, RecursiveBinarySearch.binarySearchRecursive(array, 1000, 0, array.length - 1));
        assertEquals(0, RecursiveBinarySearch.binarySearchRecursive(array, 1, 0, array.length - 1)); // Lower edge case
    }
}
