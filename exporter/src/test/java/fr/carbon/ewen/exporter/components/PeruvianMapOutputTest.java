package fr.carbon.ewen.exporter.components;

import fr.carbon.ewen.domain.components.PeruvianMap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.carbon.ewen.exporter.components.AExporterHelper.testExporter;

class PeruvianMapOutputTest {

    @Test
    void shouldExportData() {
        testExporter(
            new PeruvianMapExporter(),
            List.of(
                    new PeruvianMap(1, 2),
                    new PeruvianMap(2, 1)
            ),
            List.of(
                    "C - 1 - 2",
                    "C - 2 - 1"
            )
        );
    }
}
