package fr.carbon.ewen.domain.components;

import fr.carbon.ewen.domain.general.Position;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PeruvianMap(@PositiveOrZero int maxWidth, @PositiveOrZero int maxHeight) {


    /**
     * Check if the given position is inside the map.
     * @param position the position to check.
     * @return true if the position is inside the map, false otherwise.
     */
    public boolean isInside(@NotNull Position position) {
        var validX = 0 <= position.width() && position.width() < maxWidth;
        var validY = 0 <= position.height() && position.height() < maxHeight;
        return validX && validY;
    }
}
