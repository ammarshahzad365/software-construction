package lab7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveFileSearchTest {

    private Path testDir;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary directory for testing
        testDir = Files.createTempDirectory("testDir");

        // Creating a directory structure for testing
        Files.createFile(testDir.resolve("file1.txt"));                     // Root file
        Files.createDirectories(testDir.resolve("sub_dir1"));
        Files.createFile(testDir.resolve("sub_dir1/file2.txt"));            // File in subdirectory
        Files.createDirectories(testDir.resolve("sub_dir2"));
        Files.createFile(testDir.resolve("sub_dir2/duplicate.txt"));        // Duplicate named file
        Files.createDirectories(testDir.resolve("empty_dir"));              // Empty directory
        Files.createFile(testDir.resolve("sub_dir1/duplicate.txt"));        // Another duplicate file
        Files.createFile(testDir.resolve("sp&ci@l.txt"));                   // File with special characters
        Files.createFile(testDir.resolve("file with spaces.txt"));          // File with spaces
    }

    @AfterEach
    void tearDown() throws IOException {
        // Delete the temporary directory after tests are completed
        Files.walk(testDir)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void testFileFoundInRootDirectory() {
        File directory = testDir.toFile();
        assertTrue(RecursiveFileSearch.searchFile(directory, "file1.txt"));
    }

    

	@Test
    void testFileFoundInNestedSubdirectory() {
        File directory = testDir.toFile();
        assertTrue(RecursiveFileSearch.searchFile(directory, "file2.txt"));
    }

    @Test
    void testFileNotFound() {
        File directory = testDir.toFile();
        assertFalse(RecursiveFileSearch.searchFile(directory, "nonexistent.txt"));
    }

    @Test
    void testDuplicateFileNames() {
        File directory = testDir.toFile();
        // Checking if any one of the duplicates is found
        assertTrue(RecursiveFileSearch.searchFile(directory, "duplicate.txt"));
    }

    @Test
    void testEmptyDirectory() {
        File emptyDir = testDir.resolve("empty_dir").toFile();
        assertFalse(RecursiveFileSearch.searchFile(emptyDir, "anyfile.txt"));
    }

    @Test
    void testNonExistentDirectory() {
        File nonExistentDir = Paths.get("non_existent_dir").toFile();
        assertThrows(IllegalArgumentException.class, () -> {
            RecursiveFileSearch.searchFile(nonExistentDir, "file.txt");
        });
    }

    @Test
    void testFileWithSpecialCharacters() {
        File directory = testDir.toFile();
        assertTrue(RecursiveFileSearch.searchFile(directory, "sp&ci@l.txt"));
    }

    @Test
    void testFileWithSpaces() {
        File directory = testDir.toFile();
        assertTrue(RecursiveFileSearch.searchFile(directory, "file with spaces.txt"));
    }

    @Test
    void testCaseSensitiveSearch() {
        File directory = testDir.toFile();
        assertFalse(RecursiveFileSearch.searchFile(directory, "File1.txt")); // Assuming case-sensitive search
    }
}
