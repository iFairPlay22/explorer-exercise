package fr.carbon.ewen.exporter.components;


import fr.carbon.ewen.domain.components.Mountain;
import jakarta.validation.constraints.NotNull;

public class MountainExporter extends AExporter<Mountain> {

    @Override
    public @NotNull String export(@NotNull Mountain mountain) {
        return String.format(
            "M - %d - %d",
            mountain.position().width(),
            mountain.position().height()
        );
    }
}
