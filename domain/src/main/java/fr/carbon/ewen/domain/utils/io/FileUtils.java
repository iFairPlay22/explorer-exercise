package fr.carbon.ewen.domain.utils.io;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Slf4j
/**
 * Utility class for file operations.
 */
public class FileUtils {

    /**
     * Reads all lines from a resource file.
     * @param resourceFileName the name of the resource file.
     * @return All the lines from the resource file.
     * @throws IOException if an error occurs while reading the file.
     */
    public static List<String> readLines(@NotNull String resourceFileName) throws IOException {
        return Files.readAllLines(Path.of(resourceFileName));
    }

    /**
     * Writes the given lines to a resource file.
     * @param resourceFileName the name of the resource file.
     * @param lines the lines to write to the resource file.
     * @throws IOException if an error occurs while writing the file.
     */
    public static void writeLines(@NotNull String resourceFileName, @NotNull List<@NotNull String> lines) throws IOException {
        Files.write(Path.of(resourceFileName), lines);
    }

    /**
     * Creates a temporary file with the given content.
     * @param content the content to write to the temporary file.
     * @return the temporary file.
     * @throws IOException if an error occurs while creating the temporary file.
     */
    public static File useTmpFile(@NotNull List<@NotNull String> content) throws IOException {
        File tempFile = File.createTempFile(String.valueOf(UUID.randomUUID()), ".tmp");
        FileUtils.writeLines(tempFile.getPath(), content);
        tempFile.deleteOnExit();
        return tempFile;
    }
}