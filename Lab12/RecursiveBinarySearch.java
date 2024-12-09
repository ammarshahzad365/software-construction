package com.amfk.lab12;

import java.util.ArrayList;
import java.util.List;

public class RecursiveBinarySearch {

    /**
     * Performs a recursive binary search on a sorted array to find the index of a target value.
     * 
     * @param array the sorted array of integers to search in
     * @param target the target value to search for
     * @param left the leftmost index of the search range
     * @param right the rightmost index of the search range
     * @return the index of the target value if found, or -1 if the target is not in the array
     * 
     * Preconditions:
     * - The array must be sorted in ascending order.
     * - `left` and `right` indices should define a valid subrange of the array.
     * 
     * Postconditions:
     * - If the target is found, its index is returned.
     * - If the target is not found, -1 is returned.
     */
    public static int binarySearchRecursive(int[] array, int target, int left, int right) {
        if (array == null || left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (array[mid] == target) {
            return mid;
        } else if (array[mid] > target) {
            return binarySearchRecursive(array, target, left, mid - 1);
        } else {
            return binarySearchRecursive(array, target, mid + 1, right);
        }
    }

    /**
     * Performs a recursive binary search to find all indices of a target value in a sorted array.
     * 
     * @param array the sorted array of integers to search in
     * @param target the target value to search for
     * @param left the leftmost index of the search range
     * @param right the rightmost index of the search range
     * @return a list of all indices where the target value appears
     * 
     * Preconditions:
     * - The array must be sorted in ascending order.
     * - `left` and `right` indices should define a valid subrange of the array.
     * 
     * Postconditions:
     * - If the target appears in the array, all indices of the target are returned in ascending order.
     * - If the target does not appear, an empty list is returned.
     */
    public static List<Integer> binarySearchAllIndices(int[] array, int target, int left, int right) {
        List<Integer> result = new ArrayList<>();
        if (array == null || left > right) {
            return result;
        }
        int mid = left + (right - left) / 2;
        if (array[mid] == target) {
            // Add indices in sorted order
            result.add(mid);
            result.addAll(binarySearchAllIndices(array, target, left, mid - 1));
            result.addAll(binarySearchAllIndices(array, target, mid + 1, right));
            result.sort(Integer::compareTo);
        } else if (array[mid] > target) {
            result.addAll(binarySearchAllIndices(array, target, left, mid - 1));
        } else {
            result.addAll(binarySearchAllIndices(array, target, mid + 1, right));
        }
        return result;
    }

    /**
     * Performs a recursive binary search on a sorted array of strings to find the index of a target value.
     * 
     * @param array the sorted array of strings to search in
     * @param target the target string to search for
     * @param left the leftmost index of the search range
     * @param right the rightmost index of the search range
     * @return the index of the target string if found, or -1 if the target is not in the array
     * 
     * Preconditions:
     * - The array must be sorted lexicographically.
     * - `left` and `right` indices should define a valid subrange of the array.
     * 
     * Postconditions:
     * - If the target is found, its index is returned.
     * - If the target is not found, -1 is returned.
     */
    public static int binarySearchRecursive(String[] array, String target, int left, int right) {
        if (array == null || left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (array[mid].equals(target)) {
            return mid;
        } else if (array[mid].compareTo(target) > 0) {
            return binarySearchRecursive(array, target, left, mid - 1);
        } else {
            return binarySearchRecursive(array, target, mid + 1, right);
        }
    }
}
