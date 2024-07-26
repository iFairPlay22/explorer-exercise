package fr.carbon.ewen.exporter.components;

import jakarta.validation.constraints.NotNull;

public abstract class AExporter<T> {
    public abstract @NotNull String export(@NotNull T item);
}
