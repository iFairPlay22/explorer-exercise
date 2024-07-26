package fr.carbon.ewen.output;

import fr.carbon.ewen.domain.Simulation;
import fr.carbon.ewen.domain.components.Explorer;
import fr.carbon.ewen.domain.components.Mountain;
import fr.carbon.ewen.domain.components.PeruvianMap;
import fr.carbon.ewen.domain.components.Treasure;
import fr.carbon.ewen.domain.general.Position;
import fr.carbon.ewen.errors.SimulationValidationException;
import fr.carbon.ewen.input.SimulationImporter;
import fr.carbon.ewen.utils.io.FileUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static fr.carbon.ewen.FileHelper.useTmpFile;
import static fr.carbon.ewen.domain.components.Explorer.ExplorerAction.*;
import static fr.carbon.ewen.domain.general.Orientation.NORTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimulationExporterTest {

    @Test
    public void shouldExportData() throws Exception {
        assertThat(
            testExport(
                new Simulation(
                    new PeruvianMap(3, 3),
                    List.of(
                        new Mountain(new Position(0, 0)),
                        new Mountain(new Position(1, 1))
                    ),
                    List.of(
                        new Treasure(new Position(2, 2), new Treasure.TreasureState(1))
                    ),
                    List.of(
                        new Explorer(
                            "explorer1",
                            List.of(MOVE_FORWARD, ROTATE_LEFT, ROTATE_RIGHT),
                                new Explorer.ExplorerState(new Position(0, 1), NORTH, 1)
                        )
                    )
                )
            )
        ).isEqualTo(
                List.of(
                        "C - 3 - 3",
                        "M - 0 - 0",
                        "M - 1 - 1",
                        "T - 2 - 2 - 1",
                        "A - explorer1 - 0 - 1 - N - 1"
                )
        );
    }

    List<String> testExport(@NotNull Simulation givenSimulation) throws Exception {
        var exportFile = useTmpFile(List.of()).getPath();
        SimulationExporter exporter = new SimulationExporter();

        exporter.export(givenSimulation, exportFile);
        return FileUtils.readLines(exportFile);
    }
}
