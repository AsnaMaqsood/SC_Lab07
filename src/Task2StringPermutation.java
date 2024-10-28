import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Task2StringPermutation {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        // Greeting and User Input
        System.out.println("------------------------------------------------------------------");
        System.out.println("Welcome to the String Permutation Generator!");
        System.out.print("Please enter a string: ");
        String userInput = inputScanner.nextLine().trim();

        // Check if input is empty
        if (userInput.isEmpty()) {
            System.out.println("You did not enter anything.");
            return;
        }

        // Ask user about excluding duplicates
        System.out.print("Would you like to exclude duplicate characters? (yes/no): ");
        boolean excludeDuplicates = inputScanner.nextLine().trim().equalsIgnoreCase("yes");

        // Measure time for recursive permutations
        List<String> recursiveResults = new ArrayList<>();
        long startTimeRecursive = System.nanoTime();
        findPermutations(userInput, "", recursiveResults, excludeDuplicates);
        long endTimeRecursive = System.nanoTime();
        long durationRecursive = endTimeRecursive - startTimeRecursive;

        // Display recursive results
        System.out.println("\n*************************************************");
        System.out.println("Generated permutations (recursive method):");
        for (String perm : recursiveResults) {
            System.out.println(perm);
        }
        System.out.println("Time taken by recursive method: " + durationRecursive / 1_000_000 + " ms");

        // Measure time for iterative permutations
        long startTimeIterative = System.nanoTime();
        List<String> iterativeResults = findPermutationsIteratively(userInput, excludeDuplicates);
        long endTimeIterative = System.nanoTime();
        long durationIterative = endTimeIterative - startTimeIterative;

        // Display iterative results
        System.out.println("\n*************************************************");
        System.out.println("Generated permutations (non-recursive method):");
        for (String perm : iterativeResults) {
            System.out.println(perm);
        }
        System.out.println("Time taken by non-recursive method: " + durationIterative / 1_000_000 + " ms");

        inputScanner.close();
    }

    // Recursive function to generate permutations
    private static void findPermutations(String currentString, String currentPrefix, List<String> results, boolean excludeDuplicates) {
        int length = currentString.length();
        if (length == 0) {
            results.add(currentPrefix);
        } else {
            // To track duplicates
            HashSet<Character> encounteredChars = new HashSet<>();
            for (int index = 0; index < length; index++) {
                // Skip duplicate characters if requested
                if (excludeDuplicates && encounteredChars.contains(currentString.charAt(index))) {
                    continue;
                }
                encounteredChars.add(currentString.charAt(index));
                // Recur with the remaining characters
                findPermutations(currentString.substring(0, index) + currentString.substring(index + 1), currentPrefix + currentString.charAt(index), results, excludeDuplicates);
            }
        }
    }

    // Non-recursive function to generate permutations
    private static List<String> findPermutationsIteratively(String currentString, boolean excludeDuplicates) {
        List<String> results = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(currentString);
        boolean[] isUsed = new boolean[currentString.length()];

        // Generate permutations iteratively
        iterativePermutationHelper(stringBuilder, isUsed, "", results, excludeDuplicates);
        return results;
    }

    private static void iterativePermutationHelper(StringBuilder stringBuilder, boolean[] isUsed, String current, List<String> results, boolean excludeDuplicates) {
        if (current.length() == stringBuilder.length()) {
            results.add(current);
            return;
        }

        HashSet<Character> encounteredChars = new HashSet<>();

        for (int index = 0; index < stringBuilder.length(); index++) {
            if (isUsed[index]) continue;

            // Skip duplicate characters if requested
            if (excludeDuplicates && encounteredChars.contains(stringBuilder.charAt(index))) {
                continue;
            }

            encounteredChars.add(stringBuilder.charAt(index));
            isUsed[index] = true;
            iterativePermutationHelper(stringBuilder, isUsed, current + stringBuilder.charAt(index), results, excludeDuplicates);
            // Backtrack to previous state
            isUsed[index] = false; 
        }
    }
}
