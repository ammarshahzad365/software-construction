package lab7;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Scanner;

public class StringPermutations {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter string to generate permutations: ");
		String input = scan.nextLine();  // You can change the input to test
		System.out.println("Exclude duplicate permutations (Y/N): ");
		String userInput2 = scan.nextLine();
		boolean dupPerm = true;
		if(userInput2=="N") {
			dupPerm = false;
		}
		List<String> permutations;
		if (dupPerm) {
			permutations = generateUniquePermutations(input);
		}
		else {
			permutations = generatePermutations(input);
		}
        System.out.println("Permutations of \"" + input + "\": " + permutations);
    }

    // Method to generate all permutations of a given string
    public static List<String> generatePermutations(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return result;
        }
        permute(str, 0, str.length() - 1, result);
        return result;
    }

    // Recursive function to generate permutations
    private static void permute(String str, int left, int right, List<String> result) {
        if (left == right) {
            result.add(str);
        } else {
            for (int i = left; i <= right; i++) {
                str = swap(str, left, i);
                permute(str, left + 1, right, result);
                str = swap(str, left, i);  // Backtrack to original configuration
            }
        }
    }

    // Helper method to swap characters in a string
    private static String swap(String str, int i, int j) {
        char[] chars = str.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return new String(chars);
    }
    
    
    public static List<String> generateUniquePermutations(String str) {
        Set<String> result = new HashSet<>();
        if (str == null || str.length() == 0) {
            return new ArrayList<>(result);
        }
        permuteUnique(str, 0, str.length() - 1, result);
        return new ArrayList<>(result);
    }

    private static void permuteUnique(String str, int left, int right, Set<String> result) {
        if (left == right) {
            result.add(str);
        } else {
            for (int i = left; i <= right; i++) {
                str = swap(str, left, i);
                permuteUnique(str, left + 1, right, result);
                str = swap(str, left, i);  // Backtrack
            }
        }
    }
    public static List<String> generatePermutationsIterative(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return result;
        }
        char[] chars = str.toCharArray();
        result.add(new String(chars));

        int[] indexes = new int[chars.length];
        int i = 0;
        while (i < chars.length) {
            if (indexes[i] < i) {
                swap(chars, i % 2 == 0 ? 0 : indexes[i], i);
                result.add(new String(chars));
                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
        }
        return result;
    }

    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }


}
