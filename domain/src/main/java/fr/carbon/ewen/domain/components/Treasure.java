package fr.carbon.ewen.domain.components;

import fr.carbon.ewen.domain.general.Position;
import fr.carbon.ewen.domain.exceptions.CollisionException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record Treasure(
    @NotNull @Valid Position position,
    @NotNull @Valid TreasureState state
) implements CollisionListener {

    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Return true if the treasure can be collected, false otherwise.
     * @return true is the count is positive.
     */
    public boolean canBeCollected() {
        return 0 < state.count;
    }

    @Override
    public void onCollisionWith(@NotNull CollisionListener collisionListener) throws CollisionException {
        switch (collisionListener) {
            case Explorer explorer:
                if (canBeCollected()) {
                    log.info("{} was collected by explorer {}", this, explorer);
                    state.count -= 1;
                } else {
                    log.warn("{} could not be collected by {}", this, explorer);
                }
                break;
            case Mountain mountain:
                log.warn("{} collided with mountain {}", this, mountain);
                throw new CollisionException(this, mountain);
            case Treasure treasure:
                log.warn("{} collided with treasure {}", this, treasure);
                throw new CollisionException(this, treasure);
        }
    }

    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Getter
    public static class TreasureState {
        // Mutable fields
        private @PositiveOrZero int count;
    }

}
