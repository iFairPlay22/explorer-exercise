package fr.carbon.ewen.errors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CouldNotParseLineException extends Exception {
    private final String line;

    @Override
    public String getMessage() {
        return String.format(
            "Could not parse line: %s",
            line
        );
    }
}
