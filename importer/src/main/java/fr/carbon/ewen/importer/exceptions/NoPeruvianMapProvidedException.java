package fr.carbon.ewen.importer.exceptions;

public class NoPeruvianMapProvidedException extends Exception{

    @Override
    public String getMessage() {
        return "No PeruvianMap provided in the input file.";
    }
}
