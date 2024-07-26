package fr.carbon.ewen.output;

import fr.carbon.ewen.domain.Simulation;
import fr.carbon.ewen.domain.components.Explorer;
import fr.carbon.ewen.domain.components.Mountain;
import fr.carbon.ewen.domain.components.Treasure;
import fr.carbon.ewen.output.components.*;
import fr.carbon.ewen.utils.io.FileUtils;
import fr.carbon.ewen.utils.validation.ValidatorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;

public class SimulationExporter {

    public void export(@NotNull Simulation simulation, @NotNull String outputFileName) throws IOException {
        PeruvianMapExporter mapExporter = new PeruvianMapExporter();
        AExporter<Mountain> mountainExporter = new MountainExporter();
        AExporter<Treasure> treasureExporter = new TreasureExporter();
        AExporter<Explorer> explorerExporter = new ExplorerExporter();

        List<String> export = StreamEx.<String>empty()
            .append(mapExporter.export(simulation.getMap()))
            .append(simulation.getMountains().stream().map(mountainExporter::export))
            .append(simulation.getTreasures().stream().map(treasureExporter::export))
            .append(simulation.getExplorers().stream().map(explorerExporter::export))
            .toList();

        FileUtils.writeLines(outputFileName, export);
    }

}
