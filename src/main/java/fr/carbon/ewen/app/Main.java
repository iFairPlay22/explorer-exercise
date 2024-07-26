package fr.carbon.ewen.app;

import fr.carbon.ewen.domain.Simulation;
import fr.carbon.ewen.input.SimulationImporter;
import fr.carbon.ewen.output.SimulationExporter;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            runSimulation("input.txt", "output.txt");
            log.info("Simulation completed successfully!");
        } catch (Exception e) {
            log.error("An error occurred during the simulation:", e);
        }
    }

    public static void runSimulation(@NotNull String inputFileName, @NotNull String outputFileName) throws Exception {
        Simulation simulation = new SimulationImporter().tryImport(inputFileName);
        simulation.run();
        new SimulationExporter().export(simulation, outputFileName);
    }
}
