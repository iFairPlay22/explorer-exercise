package fr.carbon.ewen;

import fr.carbon.ewen.utils.io.FileUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public class FileHelper {

    @SneakyThrows
    public static File useTmpFile(@NotNull List<@NotNull String> content) {
        File tempFile = File.createTempFile(String.valueOf(UUID.randomUUID()), ".tmp");
        FileUtils.writeLines(tempFile.getPath(), content);
        tempFile.deleteOnExit();
        return tempFile;
    }

}
