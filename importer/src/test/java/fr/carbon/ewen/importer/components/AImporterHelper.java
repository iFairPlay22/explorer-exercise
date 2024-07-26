package fr.carbon.ewen.importer.components;

import fr.carbon.ewen.importer.components.AImporter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A helper class for testing importers.
 */
public class AImporterHelper {

    /**
     * Test an importer with a given content and expected parsed items.
     * @param importer the importer to test.
     * @param given the content to import.
     * @param expected parsed items.
     * @param <T> the type of the items to parse.
     */
    static <T> void testImporter(@NotNull AImporter<@NotNull T> importer, @NotNull List<@NotNull String> given, @NotNull List<@NotNull T> expected) {
        given.forEach(importer::tryImport);
        assertThat(importer.getParsedItems()).isEqualTo(expected);
    }

}
