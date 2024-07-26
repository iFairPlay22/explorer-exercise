package fr.carbon.ewen.domain.components;

import fr.carbon.ewen.domain.general.Orientation;
import fr.carbon.ewen.domain.general.Position;
import fr.carbon.ewen.domain.exceptions.CollisionException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Explorer character.
 * @param name Name (identifier)
 * @param movements Movements to do (e.g., left, right, forward)
 * @param state Current state of the explorer
 */
@Slf4j
public record Explorer(
    @NotNull @Size(min = 3, max = 10) String name,
    @NotNull @NotEmpty List<@NotNull ExplorerAction> movements,
    @NotNull @Valid ExplorerState state
) implements CollisionListener {

    public @NotNull Position getPosition() {
        return state.position;
    }

    public void rotateLeft() {
        log.info("{} is rotating in left direction", this);
        state.orientation = state.orientation.rotateLeft();
    }

    public void rotateRight() {
        log.info("{} is rotating in right direction", this);
        state.orientation = state.orientation.rotateRight();
    }

    public void moveForward() {
        log.info("{} is moving forward", this);
        state.position = state.position.move(state.orientation);
    }

    @Override
    public void onCollisionWith(@NotNull CollisionListener collisionListener) throws CollisionException {
        switch (collisionListener) {
            case Treasure treasure:
                if (treasure.canBeCollected()) {
                    log.info("{} collected a treasure {}", this, treasure);
                    state.treasuresFound = state().treasuresFound + 1;
                } else {
                    log.warn("{} could not collect {}", this, treasure);
                }
                break;
            case Mountain mountain:
                log.warn("{} collided with mountain {}", this, mountain);
                throw new CollisionException(this, mountain);
            case Explorer explorer:
                log.warn("{} collided with explorer {}", this, explorer);
                throw new CollisionException(this, explorer);
        }
    }


    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Getter
    public static class ExplorerState {

        // Mutable fields
        private @NotNull @Valid Position position;
        private @NotNull @Valid Orientation orientation;
        private int treasuresFound;
    };

    /**
     * All possible explorer actions.
     */
    public enum ExplorerAction {
        MOVE_FORWARD,
        ROTATE_LEFT,
        ROTATE_RIGHT;
    }
}
