package fr.carbon.ewen.input.components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AImporterHelper {

    static <T> void testImporter(@NotNull AImporter<@NotNull T> importer, @NotNull List<@NotNull String> given, @NotNull List<@NotNull T> expected) {
        given.forEach(importer::tryImport);
        assertThat(importer.getParsedItems()).isEqualTo(expected);
    }

}
