package fr.carbon.ewen.input.components;

import fr.carbon.ewen.domain.components.Mountain;
import fr.carbon.ewen.domain.general.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.carbon.ewen.input.components.AImporterHelper.testImporter;
import static org.assertj.core.api.Assertions.assertThat;

class MountainInputTest {

    @Test
    void shouldImportValidData() {
        testImporter(
            new MountainImporter(),
            List.of(
                "M - 1 - 2",
                "M - 2 - 1"
            ),
            List.of(
                new Mountain(new Position(1, 2)),
                new Mountain(new Position(2, 1))
            )
        );
    }

    @Test
    void shouldNotImportInvalidData() {
        testImporter(
            new MountainImporter(),
            List.of(
                "",
                "XXX",
                "M - 1X - 2X",
                "M - -1 - -2"
            ),
            List.of()
        );
    }
}
