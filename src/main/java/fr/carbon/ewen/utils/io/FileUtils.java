package fr.carbon.ewen.utils.io;

import jakarta.validation.constraints.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
}
