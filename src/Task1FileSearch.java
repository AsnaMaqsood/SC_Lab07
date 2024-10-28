import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task1FileSearch {
    final Map<String, List<String>> filesFound = new HashMap<>();
    private final boolean isFileNameCaseSensitive;

    public static void main(String[] args) {
        // Check if the required arguments are provided
        if (args.length < 3) {
            System.out.println("Usage: java Task1FileSearch <directory_path> <file1,file2,...> <case_sensitive (true/false)>");
            return;
        }

        String directoryPath = args[0];
        String[] tempFileNames = args[1].split(",");
        boolean caseSensitive = Boolean.parseBoolean(args[2].toLowerCase());

        // Trimming whitespace from file names
        List<String> fileNames = new ArrayList<>();
        for (String name : tempFileNames) {
            fileNames.add(name.trim());
        }

        // Creating an instance of Task1FileSearch to search for files
        Task1FileSearch searchInstance = new Task1FileSearch(caseSensitive);
        searchInstance.searchFiles(new File(directoryPath), fileNames);
        searchInstance.displayResults();
    }

    // Constructor to initialize case sensitivity
    public Task1FileSearch(boolean caseSensitive) {
        this.isFileNameCaseSensitive = caseSensitive;
    }

    // Method to search files in the given directory
    public void searchFiles(File directory, List<String> fileNamesToSearch) {
        if (!directory.exists()) {
            System.out.println("The specified directory does not exist!");
            return;
        }
        if (!directory.isDirectory()) {
            System.out.println("The specified path is not a directory!");
            return;
        }

        File[] filesListInDirectory = directory.listFiles();
        if (filesListInDirectory != null) {
            for (File file : filesListInDirectory) {
                if (file.isDirectory()) {
                    System.out.println("Found a directory: " + file.getAbsolutePath());
                    searchFiles(file, fileNamesToSearch); // Recursive search
                } else {
                    for (String fileName : fileNamesToSearch) {
                        if (fileNamesMatch(file.getName(), fileName)) {
                            System.out.println("Found: " + file.getAbsolutePath() + " for " + fileName);
                            filesFound.computeIfAbsent(fileName, k -> new ArrayList<>()).add(file.getAbsolutePath());
                        }
                    }
                }
            }
        }

        // Check for files that were not found
        for (String fileName : fileNamesToSearch) {
            if (!filesFound.containsKey(fileName)) {
                System.out.println("File not found: " + fileName);
            }
        }
    }

    // Method for matching file names
    private boolean fileNamesMatch(String currentFileName, String targetFileName) {
        return isFileNameCaseSensitive ? currentFileName.equals(targetFileName) : currentFileName.equalsIgnoreCase(targetFileName);
    }

    // Displaying the results of the search
    private void displayResults() {
        if (filesFound.isEmpty()) {
            System.out.println("No files found!");
        } else {
            for (Map.Entry<String, List<String>> entry : filesFound.entrySet()) {
                String fileName = entry.getKey();
                List<String> filePaths = entry.getValue();
                System.out.println("-----------------------------------------");
                System.out.println("File Name: " + fileName);
                filePaths.forEach(System.out::println);
                System.out.println("Number of times found: " + filePaths.size());
                System.out.println("-----------------------------------------");
            }
        }
    }
}
