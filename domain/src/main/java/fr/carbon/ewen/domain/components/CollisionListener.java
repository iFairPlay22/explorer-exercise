package fr.carbon.ewen.domain.components;

import fr.carbon.ewen.domain.Simulation;
import fr.carbon.ewen.domain.exceptions.CollisionException;
import fr.carbon.ewen.domain.general.Position;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import java.util.*;

/**
 * Interface for all items that can collide with other items.
 */
public sealed interface CollisionListener permits Explorer, Treasure {

    /**
     * Get the item position.
     * @return a position
     */
    @NotNull @Valid Position getPosition();

    /**
     * Handle collision with another item.
     * @param item the colliding item
     */
    void onCollisionWith(@NotNull CollisionListener item) throws CollisionException;

    @Slf4j
    class CollisionService {
        private final @NotNull Map<@NotNull Position, @NotNull ArrayList<@NotNull CollisionListener>> positionToObject;

        public CollisionService(Simulation simulation) {
            this.positionToObject = StreamEx.<CollisionListener>empty()
                .append(simulation.getTreasures())
                .append(simulation.getExplorers())
                .toMap(CollisionListener::getPosition, item -> new ArrayList<>(Collections.singletonList(item)));
        }

        /**
         * Detect and handle collisions following a position update.
         * @param oldPosition the position to update.
         * @param current the new position of the item.
         * @throws CollisionException if an unauthorized collision occurred
         */
        public void update(Position oldPosition, CollisionListener current) throws CollisionException  {
            Position newPosition = current.getPosition();
            if (oldPosition.equals(newPosition)) {
                log.warn("CollisionService.update was called but the position is identical");
                return ;
            }

            // Remove the current item associated with the old position
            ArrayList<CollisionListener> oldCollidingItems = positionToObject.getOrDefault(oldPosition, new ArrayList<>());
            oldCollidingItems.remove(current);
            if (oldCollidingItems.isEmpty()) {
                positionToObject.remove(oldPosition);
            } else {
                positionToObject.put(oldPosition, oldCollidingItems);
            }

            // Add the current item associated with the new position
            ArrayList<CollisionListener> newCollidingItems = positionToObject.getOrDefault(newPosition, new ArrayList<>());
            for (CollisionListener newCollidingItem : newCollidingItems) {
                current.onCollisionWith(newCollidingItem);
                newCollidingItem.onCollisionWith(current);
            }
            newCollidingItems.add(current);
            positionToObject.put(newPosition, newCollidingItems);
        }
    }
}
