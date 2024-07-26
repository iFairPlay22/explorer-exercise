package fr.carbon.ewen.exporter.components;


import fr.carbon.ewen.domain.components.Explorer;
import jakarta.validation.constraints.NotNull;


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
