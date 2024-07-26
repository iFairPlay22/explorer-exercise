package fr.carbon.ewen.output.components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public abstract class AExporter<T> {
    public abstract @NotNull String export(@NotNull T item);
}
