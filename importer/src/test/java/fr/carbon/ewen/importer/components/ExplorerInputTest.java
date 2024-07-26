package fr.carbon.ewen.importer.components;

import fr.carbon.ewen.domain.components.Explorer;
import fr.carbon.ewen.domain.general.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.carbon.ewen.domain.components.Explorer.ExplorerAction.*;
import static fr.carbon.ewen.domain.general.Orientation.*;
import static fr.carbon.ewen.importer.components.AImporterHelper.testImporter;

class ExplorerInputTest {

    @Test
    void shouldImportValidData() {
        testImporter(
            new ExplorerImporter(),
            List.of(
                "A - ex1 - 1 - 2 - N - AAA",
                "A - ex2 - 2 - 3 - S - DDD",
                "A - ex3 - 3 - 4 - E - GGG",
                "A - ex4 - 4 - 5 - W - DAG"
            ),
            List.of(
                new Explorer("ex1", List.of(MOVE_FORWARD, MOVE_FORWARD, MOVE_FORWARD), new Explorer.ExplorerState(new Position(1, 2), NORTH, 0)),
                new Explorer("ex2", List.of(ROTATE_RIGHT, ROTATE_RIGHT, ROTATE_RIGHT), new Explorer.ExplorerState(new Position(2, 3), SOUTH, 0)),
                new Explorer("ex3", List.of(ROTATE_LEFT, ROTATE_LEFT, ROTATE_LEFT), new Explorer.ExplorerState(new Position(3, 4), EAST, 0)),
                new Explorer("ex4", List.of(ROTATE_RIGHT, MOVE_FORWARD, ROTATE_LEFT), new Explorer.ExplorerState(new Position(4, 5), WEST, 0))
            )
        );
    }

    @Test
    void shouldNotImportInvalidData() {
        testImporter(
            new ExplorerImporter(),
            List.of(
                "",
                "XXX",
                "A - X - X - X - X - X",
                "A - ex1 - 1 - 2 - X - AAA",
                "A - ex1 - 1 - 2 - S - ",
                "A - ex1 - 1 - 2 - S - XXX"
            ),
            List.of()
        );
    }
}
