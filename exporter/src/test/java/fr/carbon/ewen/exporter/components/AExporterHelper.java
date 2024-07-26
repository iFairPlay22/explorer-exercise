package fr.carbon.ewen.exporter.components;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class AExporterHelper {

    static <T> void testExporter(@NotNull AExporter<T> exporter, @NotNull List<@NotNull T> given, @NotNull List<@NotNull String> expected) {
        List<String> result = given.stream().map(exporter::export).toList();
        assertThat(result).isEqualTo(expected);
    }

}
