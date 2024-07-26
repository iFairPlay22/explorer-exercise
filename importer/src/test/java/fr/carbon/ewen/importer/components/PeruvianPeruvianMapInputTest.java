package fr.carbon.ewen.importer.components;

import fr.carbon.ewen.domain.components.PeruvianMap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.carbon.ewen.importer.components.AImporterHelper.testImporter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PeruvianPeruvianMapInputTest {
    @Test
    void shouldImportValidData() {
        testImporter(
            new PeruvianMapImporter(),
            List.of(
                "C - 1 - 2",
                "C - 2 - 1"
            ),
            List.of(
                new PeruvianMap(1, 2),
                new PeruvianMap(2, 1)
            )
        );
    }

    @Test
    void shouldNotImportInvalidData() {
        testImporter(
            new PeruvianMapImporter(),
            List.of(
                "",
                "XXX",
                "C - 1X - 2X",
                "C - -1 - -2"
            ),
            List.of()
        );
    }
}
