package fr.carbon.ewen.importer;

import fr.carbon.ewen.domain.Simulation;
import fr.carbon.ewen.domain.components.Explorer;
import fr.carbon.ewen.domain.components.Mountain;
import fr.carbon.ewen.domain.components.PeruvianMap;
import fr.carbon.ewen.domain.components.Treasure;
import fr.carbon.ewen.domain.exceptions.SimulationValidationException;
import fr.carbon.ewen.domain.general.Position;
import fr.carbon.ewen.domain.utils.io.FileUtils;
import fr.carbon.ewen.importer.exceptions.CouldNotParseLineException;
import fr.carbon.ewen.importer.exceptions.NoPeruvianMapProvidedException;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import java.util.List;

import static fr.carbon.ewen.domain.components.Explorer.ExplorerAction.*;
import static fr.carbon.ewen.domain.general.Orientation.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimulationImporterTest {

    @Test
    public void shouldImportValidData() throws Exception {
        assertThat(testImport(
            List.of(
                "C - 3 - 3",
                "M - 0 - 0",
                "M - 1 - 1",
                "T - 2 - 2 - 1",
                "A - explorer1 - 0 - 1 - S - GADAADAA"
            )
        )).isEqualTo(
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
                                List.of(ROTATE_LEFT, MOVE_FORWARD, ROTATE_RIGHT, MOVE_FORWARD, MOVE_FORWARD, ROTATE_RIGHT, MOVE_FORWARD, MOVE_FORWARD),
                                new Explorer.ExplorerState(new Position(0, 1), SOUTH, 0)
                        )
                )
        ));
    }

    @Test
    public void shouldTolerateOverlappingExplorerAndTreasure() throws Exception {
        assertThat(
            testImport(
                List.of(
                    "C - 3 - 3",
                    "T - 0 - 0 - 1",
                    "A - exp1 - 0 - 0 - S - A"
                )
            )
        ).isEqualTo(
            new Simulation(
                new PeruvianMap(3, 3),
                List.of(),
                List.of(new Treasure(new Position(0, 0), new Treasure.TreasureState(1))),
                List.of(new Explorer("exp1", List.of(MOVE_FORWARD), new Explorer.ExplorerState(new Position(0, 0), SOUTH, 0)))
            )
        );
    }

    @Test
    public void shouldOnlyTakeIntoAccountTheFirstMap() throws Exception {
        assertThat(testImport(List.of("C - 1 - 1", "C - 2 - 2"))).isEqualTo(new Simulation(
                new PeruvianMap(1, 1),
                List.of(),
                List.of(),
                List.of()
        ));
    }

    @Test
    public void shouldNotImportWhenMultipleExplorerNames() {
        List.of(
            List.of("C - 3 - 3", "A - exp1 - 0 - 0 - S - AAA", "A - exp1 - 0 - 0 - S - AAA"),
            List.of("C - 3 - 3", "A - exp1 - 0 - 0 - S - AAA", "A - exp1 - 1 - 1 - S - AAA")
        ).forEach(content -> assertThrows(SimulationValidationException.class, () -> testImport(content)));
    }

    @Test
    public void shouldNotImportWhenOutOfBoundPositions() {
        List.of(
            List.of("C - 3 - 3", "M - 1000 - 0"),
            List.of("C - 3 - 3", "M - 0 - 1000"),
            List.of("C - 3 - 3", "T - 1000 - 10000 - 1"),
            List.of("C - 3 - 3", "A - exp1 - 1000 - 10000 - S - AAA")
        ).forEach(content -> assertThrows(SimulationValidationException.class, () -> testImport(content)));
    }


    @Test
    public void shouldNotImportWhenPositionDuplicates() {
        List.of(
            List.of("C - 3 - 3", "M - 0 - 0", "M - 0 - 0"),
            List.of("C - 3 - 3", "T - 0 - 0 - 1", "T - 0 - 0 - 1"),
            List.of("C - 3 - 3", "A - exp1 - 0 - 0 - S - A", "A - exp1 - 0 - 0 - S - A"),
            List.of("C - 3 - 3", "M - 0 - 0", "T - 0 - 0 - 1"),
            List.of("C - 3 - 3", "M - 0 - 0", "A - exp1 - 0 - 0 - S - A")
        ).forEach(content -> assertThrows(SimulationValidationException.class, () -> testImport(content)));
    }

    @Test
    public void shouldNotImportWhenBadLineFormat() {
        List.of(
            List.of(""),
            List.of("XXX"),
            List.of("M - 1 - 1", "XXX")
        ).forEach(content -> assertThrows(CouldNotParseLineException.class, () -> testImport(content)));
    }

    @Test
    public void shouldNotImportWhenNoMapProvided() {
        List.of(
            List.of("M - 0 - 0"),
            List.of("T - 0 - 0 - 1"),
            List.of("A - exp1 - 0 - 0 - S - AAA")
        ).forEach(content -> assertThrows(NoPeruvianMapProvidedException.class, () -> testImport(content)));
    }

    @NotNull Simulation testImport(@NotNull List<@NotNull String> content) throws Exception {
        var file = FileUtils.useTmpFile(content).getPath();
        SimulationImporter importer = new SimulationImporter();
        return importer.tryImport(file);
    }
}
