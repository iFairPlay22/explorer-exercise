package fr.carbon.ewen.exporter.components;

import jakarta.validation.constraints.NotNull;

/**
 * An abstract exporter for a given type.
 * @param <T> the type of the item to export.
 */
public abstract class AExporter<T> {
    public abstract @NotNull String export(@NotNull T item);
}
