package fr.carbon.ewen.simulation;

import fr.carbon.ewen.domain.exceptions.CollisionException;
import fr.carbon.ewen.domain.utils.io.FileUtils;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

    @Test
    public void shouldRunSimulationWith1Player() throws Exception {
        assertThat(testSimulation(
            List.of(
                "C - 3 - 3",
                "M - 0 - 0",
                "M - 1 - 1",
                "T - 2 - 2 - 1",
                "A - explorer1 - 1 - 0 - S - GADAADAA"
            )
        )).isEqualTo(List.of(
                "C - 3 - 3",
                "M - 0 - 0",
                "M - 1 - 1",
                "T - 2 - 2 - 0",
                "A - explorer1 - 0 - 2 - W - 1"
        ));
    }

    @Test
    public void shouldRunSimulationWith2Players() throws Exception {
        assertThat(testSimulation(
            List.of(
                "C - 1 - 5",
                "T - 0 - 2 - 1",
                "T - 0 - 3 - 1",
                "T - 0 - 4 - 1",
                "A - explorer1 - 0 - 1 - S - AAA",
                "A - explorer2 - 0 - 0 - S - AAA"
            )
        )).isEqualTo(List.of(
            "C - 1 - 5",
            "T - 0 - 2 - 0",
            "T - 0 - 3 - 0",
            "T - 0 - 4 - 0",
            "A - explorer1 - 0 - 4 - S - 3",
            "A - explorer2 - 0 - 3 - S - 0"
        ));
    }

    @Test
    public void shouldRunSimulationAndGetsBlocked() throws Exception {
        assertThat(testSimulation(
            List.of(
                "C - 3 - 3",
                "M - 0 - 0",
                "M - 1 - 1",
                "T - 2 - 2 - 1",
                "A - explorer1 - 1 - 0 - S - AAAGAAADAAADAAA"
            )
        )).isEqualTo(List.of(
            "C - 3 - 3",
            "M - 0 - 0",
            "M - 1 - 1",
            "T - 2 - 2 - 0",
            "A - explorer1 - 0 - 2 - W - 1"
        ));
    }

    @Test
    public void playerShouldStopAndNotExitMap() throws Exception {
        assertThat(testSimulation(List.of("C - 1 - 1", "A - explorer1 - 0 - 0 - N - A")))
            .isEqualTo(List.of("C - 1 - 1", "A - explorer1 - 0 - 0 - N - 0"));
        assertThat(testSimulation(List.of("C - 1 - 1", "A - explorer1 - 0 - 0 - S - A")))
            .isEqualTo(List.of("C - 1 - 1", "A - explorer1 - 0 - 0 - S - 0"));
        assertThat(testSimulation(List.of("C - 1 - 1", "A - explorer1 - 0 - 0 - W - A")))
            .isEqualTo(List.of("C - 1 - 1", "A - explorer1 - 0 - 0 - W - 0"));
        assertThat(testSimulation(List.of("C - 1 - 1", "A - explorer1 - 0 - 0 - E - A")))
            .isEqualTo(List.of("C - 1 - 1", "A - explorer1 - 0 - 0 - E - 0"));

    }

    @Test
    public void playerShouldNotCollideWithMountain() throws Exception {
        assertThat(testSimulation(List.of("C - 1 - 2", "M - 0 - 1", "A - explorer1 - 0 - 0 - S - A")))
            .isEqualTo(List.of("C - 1 - 2", "M - 0 - 1", "A - explorer1 - 0 - 0 - S - 0"));
        assertThat(testSimulation(List.of("C - 1 - 2", "M - 0 - 1", "A - explorer1 - 0 - 0 - S - AAADDDDAAAGGGGAAADGAAGDAA")))
            .isEqualTo(List.of("C - 1 - 2", "M - 0 - 1", "A - explorer1 - 0 - 0 - S - 0"));
    }

    @Test
    public void shouldStopSimulationWhenCollisionExplorerExplorer() {
        assertThrows(
            CollisionException.class,
            () -> testSimulation(
                List.of(
                    "C - 2 - 1",
                    "A - explorer1 - 0 - 0 - E - A",
                    "A - explorer2 - 1 - 0 - E - A"
                )
            )
        );
    }

    @Test
    public void shouldCollectTreasureWhenCollisionExplorerTreasure() throws Exception {
        assertThat(
            testSimulation(
                List.of(
                    "C - 2 - 1",
                    "A - explorer1 - 0 - 0 - E - ADDADDADDADDA",
                    "T - 1 - 0 - 2"
                )
            )
        ).isEqualTo(
            List.of(
                "C - 2 - 1",
                "T - 1 - 0 - 0",
                "A - explorer1 - 1 - 0 - E - 2"
            )
        );
    }

    public List<@NotNull String> testSimulation(@NotNull List<@NotNull String> given) throws Exception {
        var importFile = FileUtils.useTmpFile(given).getPath();
        var exportFile = FileUtils.useTmpFile(List.of()).getPath();

        Main.runSimulation(importFile, exportFile);
        return FileUtils.readLines(exportFile);
    }
}
