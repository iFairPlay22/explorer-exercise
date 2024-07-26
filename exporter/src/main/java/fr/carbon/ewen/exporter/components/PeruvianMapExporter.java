package fr.carbon.ewen.exporter.components;


import fr.carbon.ewen.domain.components.PeruvianMap;
import jakarta.validation.constraints.NotNull;

public class PeruvianMapExporter extends AExporter<PeruvianMap> {

    @Override
    public @NotNull String export(@NotNull PeruvianMap map) {
        return String.format(
            "C - %d - %d",
            map.maxWidth(),
            map.maxHeight()
        );
    }
}
