import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Task1FileSearchTest {
    private File temporaryDirectory;

    @BeforeEach
    public void initialize() throws IOException {
        // Create a temporary directory for test files
        temporaryDirectory = Files.createTempDirectory("testDirectory").toFile();
        temporaryDirectory.deleteOnExit(); // Ensure the directory is deleted after tests

        // Create sample files for testing
        createSampleFile("document1.txt", "Content of document 1.");
        createSampleFile("document2.txt", "Content of document 2.");
        createSampleFile("document3.txt", "Content of document 3.");
    }

    private void createSampleFile(String fileName, String content) throws IOException {
        File file = new File(temporaryDirectory, fileName);
        Files.writeString(file.toPath(), content);
    }

    @AfterEach
    public void cleanUp() {
        // No specific cleanup actions needed as temporaryDirectory will be deleted automatically
    }

    @Test
    public void testFileSearchCaseSensitiveMatch() {
        Task1FileSearch searchInstance = new Task1FileSearch(true);
        List<String> targetFiles = Arrays.asList("document1.txt", "document2.txt");

        searchInstance.searchFiles(temporaryDirectory, targetFiles);
        
        assertEquals(2, searchInstance.filesFound.size());
        assertTrue(searchInstance.filesFound.containsKey("document1.txt"));
        assertTrue(searchInstance.filesFound.containsKey("document2.txt"));
    }

    @Test
    public void testFileSearchCaseInsensitiveMatch() {
        Task1FileSearch searchInstance = new Task1FileSearch(false);
        List<String> targetFiles = Arrays.asList("DOCUMENT1.TXT", "document2.txt");

        searchInstance.searchFiles(temporaryDirectory, targetFiles);
        
        assertEquals(2, searchInstance.filesFound.size());
        assertTrue(searchInstance.filesFound.containsKey("DOCUMENT1.TXT"));
        assertTrue(searchInstance.filesFound.containsKey("document2.txt"));
    }

    @Test
    public void testFileSearchNoMatchFound() {
        Task1FileSearch searchInstance = new Task1FileSearch(true);
        List<String> targetFiles = Arrays.asList("missingFile.txt");

        searchInstance.searchFiles(temporaryDirectory, targetFiles);
        
        assertTrue(searchInstance.filesFound.isEmpty());
    }

    @Test
    public void testSearchInInvalidDirectory() {
        Task1FileSearch searchInstance = new Task1FileSearch(true);
        List<String> targetFiles = Arrays.asList("document1.txt");
        
        File invalidDirectory = new File("invalid_directory");
        searchInstance.searchFiles(invalidDirectory, targetFiles);
        
        assertTrue(searchInstance.filesFound.isEmpty());
    }

    @Test
    public void testSearchInNonDirectoryFilePath() {
        Task1FileSearch searchInstance = new Task1FileSearch(true);
        List<String> targetFiles = Arrays.asList("document1.txt");

        // Use a valid file path instead of a directory
        File nonDirectoryFile = new File(temporaryDirectory, "document1.txt");
        searchInstance.searchFiles(nonDirectoryFile, targetFiles);
        
        assertTrue(searchInstance.filesFound.isEmpty());
    }
}
