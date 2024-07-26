package fr.carbon.ewen.domain.exceptions;

import fr.carbon.ewen.domain.components.Explorer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExplorerOutOfMapException extends Exception {
    private final Explorer explorer;

    @Override
    public String getMessage() {
        return String.format(
            "Explorer %s is out of map",
            explorer
        );
    }
}

