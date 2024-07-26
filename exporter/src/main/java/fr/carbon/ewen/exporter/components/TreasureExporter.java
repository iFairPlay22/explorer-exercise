package fr.carbon.ewen.exporter.components;


import fr.carbon.ewen.domain.components.Treasure;
import jakarta.validation.constraints.NotNull;

public class TreasureExporter extends AExporter<Treasure> {

    @Override
    public @NotNull String export(@NotNull Treasure item) {
        return String.format(
            "T - %d - %d - %d",
            item.position().width(),
            item.position().height(),
            item.state().getCount()
        );
    }
}
