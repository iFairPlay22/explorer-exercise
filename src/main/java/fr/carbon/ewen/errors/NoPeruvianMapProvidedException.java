package fr.carbon.ewen.errors;

public class NoPeruvianMapProvidedException extends Exception{

    @Override
    public String getMessage() {
        return "No PeruvianMap provided in the input file.";
    }
}
