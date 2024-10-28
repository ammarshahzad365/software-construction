package lab7;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StringPermutationsTest {

    @Test
    void testSingleCharacterString() {
        String input = "a";
        List<String> expected = Arrays.asList("a");
        List<String> actual = StringPermutations.generatePermutations(input);
        assertEquals(expected, actual);
    }

    @Test
    void testTwoCharacterString() {
        String input = "ab";
        List<String> expected = Arrays.asList("ab", "ba");
        List<String> actual = StringPermutations.generatePermutations(input);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void testThreeCharacterString() {
        String input = "abc";
        List<String> expected = Arrays.asList("abc", "acb", "bac", "bca", "cab", "cba");
        List<String> actual = StringPermutations.generatePermutations(input);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void testStringWithDuplicates() {
        String input = "aab";
        List<String> expected = Arrays.asList("aab", "aba", "baa");
        List<String> actual = StringPermutations.generatePermutations(input);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void testEmptyString() {
        String input = "";
        List<String> expected = new ArrayList<>();  // Expecting an empty list
        List<String> actual = StringPermutations.generatePermutations(input);
        assertEquals(expected, actual);
    }

    @Test
    void testStringWithSpecialCharacters() {
        String input = "a@";
        List<String> expected = Arrays.asList("a@", "@a");
        List<String> actual = StringPermutations.generatePermutations(input);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void testLongStringPerformance() {
        String input = "abcd";
        List<String> actual = StringPermutations.generatePermutations(input);
        
        // Expected number of permutations for "abcd" is 4! = 24
        assertEquals(24, actual.size());
    }

    @Test
    void testNoDuplicatePermutations() {
        String input = "aabb";
        List<String> actual = StringPermutations.generatePermutations(input);
        
        // Check that there are no duplicate entries in the list of permutations
        Set<String> uniquePermutations = new HashSet<>(actual);
        assertEquals(uniquePermutations.size(), actual.size());
    }
}
