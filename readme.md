# Context

In this project, I implemented a mini-game where some explorers should find and collect 
treasures in a map, while trying to avoid obstacles like mountains. In particular, 
the java codebase allow to:
- Load a simulation from an input file ;
- Play this simulation ;
- Export the simulation results in an output file ;

You can find more details about the assignment by reading [this file](./assignment.pdf) 
(only available in French).

## Project structure

The project is divided into 4 java modules:
- `domain`: entities definition and simulation implementation ;  
- `importer`: simulation loading (from an input file) ;
- `exporter`: simulation results export (to an output file) ;
- `simulation`: launch the whole process (import + simulation + export) ;

## How to run the project

This is a basic maven project, based on Java 21 (`sdk use java 21.0.3-tem` with sdkman). 
Once done, you can use the following commands:
- `mvn clean`: to delete all previously compiled Java .class files and resources ;
- `mvn install`: test & package your Java project and even install/copy your built .jar/.war file into your local Maven repository ;
- `mvn exec:java -pl simulation -Dexec.mainClass=fr.carbon.ewen.simulation.Main -Dexec.args="'./_examples_/import.txt' './_examples_/export.txt'"`: to launch the main code ;
NB: You need to run `mvn install` before
NB: Make sure to provide valid absolute file paths.

You can also use the `simulation` module to launch the code for a given input and output 
file using: ``.
