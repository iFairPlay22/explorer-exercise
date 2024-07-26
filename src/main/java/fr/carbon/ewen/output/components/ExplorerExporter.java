package fr.carbon.ewen.output.components;


import fr.carbon.ewen.domain.components.Explorer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.stream.Collectors;

import static fr.carbon.ewen.domain.general.Orientation.*;


public class ExplorerExporter extends AExporter<Explorer> {


    @Override
    public @NotNull String export(@NotNull Explorer explorer) {
        return String.format(
            "A - %s - %d - %d - %s - %d",
            explorer.name(),
            explorer.getPosition().width(),
            explorer.getPosition().height(),
            switch (explorer.state().getOrientation()) {
                case NORTH: yield "N";
                case SOUTH: yield "S";
                case WEST:  yield "W";
                case EAST:  yield "E";
            },
            explorer.state().getTreasuresFound()
        );
    }
}
