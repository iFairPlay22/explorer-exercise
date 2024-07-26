package fr.carbon.ewen.exporter.components;

import fr.carbon.ewen.domain.components.Explorer;
import fr.carbon.ewen.domain.general.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.carbon.ewen.domain.general.Orientation.*;
import static fr.carbon.ewen.exporter.components.AExporterHelper.testExporter;

class ExplorerOutputTest {

    @Test
    void shouldExportData() {
        testExporter(
            new ExplorerExporter(),
            List.of(
                    new Explorer("ex1", List.of(), new Explorer.ExplorerState(new Position(1, 2), NORTH, 1)),
                    new Explorer("ex2", List.of(), new Explorer.ExplorerState(new Position(2, 3), SOUTH, 2)),
                    new Explorer("ex3", List.of(), new Explorer.ExplorerState(new Position(3, 4), EAST, 3)),
                    new Explorer("ex4", List.of(), new Explorer.ExplorerState(new Position(4, 5), WEST, 4))
            ),
            List.of(
                "A - ex1 - 1 - 2 - N - 1",
                "A - ex2 - 2 - 3 - S - 2",
                "A - ex3 - 3 - 4 - E - 3",
                "A - ex4 - 4 - 5 - W - 4"
            )
        );
    }
}
