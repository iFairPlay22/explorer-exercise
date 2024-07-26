package fr.carbon.ewen.importer;

import fr.carbon.ewen.domain.Simulation;
import fr.carbon.ewen.domain.components.Explorer;
import fr.carbon.ewen.domain.components.Mountain;
import fr.carbon.ewen.domain.components.PeruvianMap;
import fr.carbon.ewen.domain.components.Treasure;
import fr.carbon.ewen.domain.utils.io.FileUtils;
import fr.carbon.ewen.domain.utils.validation.ValidatorService;
import fr.carbon.ewen.importer.components.*;
import fr.carbon.ewen.importer.exceptions.CouldNotParseLineException;
import fr.carbon.ewen.importer.exceptions.NoPeruvianMapProvidedException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class SimulationImporter {

    public @NotNull Simulation tryImport(@NotNull String inputFileName) throws Exception {

        // IMPORT
        AImporter<PeruvianMap> mapImporter = new PeruvianMapImporter();
        AImporter<Mountain> mountainImporter = new MountainImporter();
        AImporter<Treasure> treasureImporter = new TreasureImporter();
        AImporter<Explorer> explorerImporter = new ExplorerImporter();

        var importers = List.of(mapImporter, mountainImporter, treasureImporter, explorerImporter);
        for (var line : FileUtils.readLines(inputFileName)) {

            // Ignore comment lines
            if (line.startsWith("#")) {
                log.info("Skipping comment line: {}", line);
                continue ;
            }

            // Parse line and count successful imports
            var count = importers
                .stream()
                .map(importer -> importer.tryImport(line))
                .flatMap(Optional::stream)
                .count();

            if (count == 0) {
                log.warn("No importer could parse the line: {}", line);
                throw new CouldNotParseLineException(line);
            } else if (count == 1) {
                log.info("Line parsed successfully: {}", line);
            } else {
                log.warn("More than 1 importer could parse the line: {}", line);
            }
        }

        if (1 < mapImporter.getParsedItems().size()) {
            log.warn("More than 1 map was found in the file. Only the first one is taking into account");
        }

        // VALIDATION
        return ValidatorService.validate(
            new Simulation(
                mapImporter.getParsedItems()
                    .stream()
                    .findFirst()
                    .orElseThrow(NoPeruvianMapProvidedException::new),
                mountainImporter.getParsedItems(),
                treasureImporter.getParsedItems(),
                explorerImporter.getParsedItems()
            )
        );
    }
}
