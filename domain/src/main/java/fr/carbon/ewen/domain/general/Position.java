package fr.carbon.ewen.domain.general;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Represents a position in the 2D space.
 * @param width
 * @param height
 */
public record Position(@PositiveOrZero int width, @PositiveOrZero int height) {

    /**
     * Moves the position in the right direction (by checking the given orientation).
     * @param orientation the orientation to move in.
     * @return the new position after moving.
     */
    public @NotNull Position move(@NotNull Orientation orientation) {
        Direction direction = orientation.getDirection();
        return new Position(width + direction.x(), height + direction.y());
    }
}
