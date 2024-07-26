package fr.carbon.ewen.exporter.components;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Helper class for testing exporters.
 */
public class AExporterHelper {

    /**
     * Test a given exporter with given input and expected output.
     * @param exporter the exporter to test
     * @param given input data
     * @param expected output data
     * @param <T> the type of the input data
     */
    static <T> void testExporter(@NotNull AExporter<T> exporter, @NotNull List<@NotNull T> given, @NotNull List<@NotNull String> expected) {
        List<String> result = given.stream().map(exporter::export).toList();
        assertThat(result).isEqualTo(expected);
    }

}
