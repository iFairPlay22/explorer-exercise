package fr.carbon.ewen.domain.utils.io;

import jakarta.validation.constraints.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class FileUtils {

    public static List<String> readLines(@NotNull String resourceFileName) throws IOException {
        return Files.readAllLines(Path.of(resourceFileName));
    }

    public static void writeLines(@NotNull String resourceFileName, @NotNull List<@NotNull String> lines) throws IOException {
        Files.write(Path.of(resourceFileName), lines);
    }

    public static void deleteFile(@NotNull String resourceFileName) throws IOException {
        Files.delete(Path.of(resourceFileName));
    }

    public static File useTmpFile(@NotNull List<@NotNull String> content) throws IOException {
        File tempFile = File.createTempFile(String.valueOf(UUID.randomUUID()), ".tmp");
        FileUtils.writeLines(tempFile.getPath(), content);
        tempFile.deleteOnExit();
        return tempFile;
    }
}