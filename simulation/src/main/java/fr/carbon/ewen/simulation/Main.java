package fr.carbon.ewen.simulation;

import fr.carbon.ewen.domain.Simulation;
import fr.carbon.ewen.importer.SimulationImporter;
import fr.carbon.ewen.exporter.SimulationExporter;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Main {

    /**
     * Main method to run the simulation, given an input file (1sr argument) and an output file (2nd argument).
     * @param args Command line arguments: input file path, output file path.
     */
    public static void main(String[] args) {
        switch (args.length) {
            case 0 -> log.info("No input or output file provided. Please provide an input file and an output file.");
            case 1 -> log.info("No output file provided. Please provide an output file.");
            case 2 -> {
                try {
                    runSimulation(args[0],  args[1]);
                    log.info("Simulation completed successfully!");
                } catch (Exception e) {
                    log.error("An error occurred during the simulation:", e);
                }
            }
            default -> log.info("Too many arguments provided. Please provide only one input file and one output file.");
        }
    }

    /**
     * Runs the simulation from the input file to the output file.
     * @param inputFileName The input file path.
     * @param outputFileName The output file path.
     * @throws Exception If an error occurs during the simulation.
     */
    public static void runSimulation(@NotNull String inputFileName, @NotNull String outputFileName) throws Exception {
        Simulation simulation = new SimulationImporter().tryImport(inputFileName);
        simulation.run();
        new SimulationExporter().export(simulation, outputFileName);
    }
}
