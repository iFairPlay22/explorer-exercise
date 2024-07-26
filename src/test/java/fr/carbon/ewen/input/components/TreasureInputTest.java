package fr.carbon.ewen.input.components;

import fr.carbon.ewen.domain.components.Treasure;
import fr.carbon.ewen.domain.general.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.carbon.ewen.input.components.AImporterHelper.testImporter;
import static org.assertj.core.api.Assertions.assertThat;

class TreasureInputTest {
    @Test
    void shouldImportValidData() {
        testImporter(
            new TreasureImporter(),
            List.of(
                "T - 1 - 2 - 3",
                "T - 3 - 2 - 1"
            ),
            List.of(
                new Treasure(new Position(1, 2), new Treasure.TreasureState(3)),
                new Treasure(new Position(3, 2), new Treasure.TreasureState(1))
            )
        );
    }

    @Test
    void shouldNotImportInvalidData() {
        testImporter(
            new TreasureImporter(),
            List.of(
                "",
                "XXX",
                "T - 1X - 2X - 3X",
                "T - -1 - -2 - -3"
            ),
            List.of()
        );
    }
}
