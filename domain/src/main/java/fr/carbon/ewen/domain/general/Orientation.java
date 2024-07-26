package fr.carbon.ewen.domain.general;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Orientation {
    NORTH(new Direction(0, -1)),
    SOUTH(new Direction(0, 1)),
    WEST(new Direction(-1, 0)),
    EAST(new Direction(1, 0));

    private final @NotNull @Valid Direction direction;

    /**
     * Rotates the orientation to the right.
     * @return the rotated orientation.
     */
    public @NotNull Orientation rotateRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    /**
     * Rotates the orientation to the left.
     * @return the rotated orientation.
     */
    public @NotNull Orientation rotateLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }
}