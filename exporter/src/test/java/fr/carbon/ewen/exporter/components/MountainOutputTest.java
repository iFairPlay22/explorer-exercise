package fr.carbon.ewen.exporter.components;

import fr.carbon.ewen.domain.components.Mountain;
import fr.carbon.ewen.domain.general.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.carbon.ewen.exporter.components.AExporterHelper.testExporter;

class MountainOutputTest {

    @Test
    void shouldExportData() {
        testExporter(
            new MountainExporter(),
            List.of(
                new Mountain(new Position(1, 2)),
                new Mountain(new Position(2, 1))
            ),
            List.of(
                "M - 1 - 2",
                "M - 2 - 1"
            )
        );
    }
}
