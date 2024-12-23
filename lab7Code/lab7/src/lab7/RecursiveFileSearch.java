package lab7;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursiveFileSearch {
	private static int occurrenceCount = 0;
    private static boolean caseSensitive = true;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java RecursiveFileSearch <directory_path> <file_name> [--ignore-case]");
            return;
        }

        String directoryPath = args[0];
        List<String> fileNames = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("--ignore-case")) {
                caseSensitive = false;
            } else {
                fileNames.add(args[i]);
            }
        }

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Error: The specified path is not a directory or does not exist.");
            return;
        }

        for (String fileName : fileNames) {
            occurrenceCount = 0;
            System.out.println("Searching for: " + fileName);
            searchFile(directory, fileName);
            if (occurrenceCount == 0) {
                System.out.println("File not found: " + fileName);
            } else {
                System.out.println("Occurrences of " + fileName + ": " + occurrenceCount);
            }
        }
    }

    public static void searchFile(File directory, String fileName) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                searchFile(file, fileName);
            } else if (matches(file.getName(), fileName)) {
                System.out.println("File found: " + file.getAbsolutePath());
                occurrenceCount++;
            }
        }
    }

    private static boolean matches(String fileName, String target) {
        return caseSensitive ? fileName.equals(target) : fileName.equalsIgnoreCase(target);
    }
}
