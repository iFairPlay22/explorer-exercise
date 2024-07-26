package fr.carbon.ewen.exporter.components;

import fr.carbon.ewen.domain.components.Treasure;
import fr.carbon.ewen.domain.general.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.carbon.ewen.exporter.components.AExporterHelper.testExporter;

class TreasureOutputTest {

    @Test
    void shouldExportData() {
        testExporter(
            new TreasureExporter(),
            List.of(
                new Treasure(new Position(1, 2), new Treasure.TreasureState(3)),
                new Treasure(new Position(3, 2), new Treasure.TreasureState(1))
            ),
            List.of(
                "T - 1 - 2 - 3",
                "T - 3 - 2 - 1"
            )
        );
    }
}
