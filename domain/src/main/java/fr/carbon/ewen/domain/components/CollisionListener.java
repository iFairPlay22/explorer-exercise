package fr.carbon.ewen.domain.components;

import fr.carbon.ewen.domain.exceptions.CollisionException;
import fr.carbon.ewen.domain.general.Position;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import one.util.streamex.StreamEx;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Interface for all items that can collide with other items.
 */
public sealed interface CollisionListener permits Explorer, Mountain, Treasure {

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

    /**
     * Detect and handle collisions among all items.
     * @param items the items to check for collisions
     * @throws CollisionException if an unauthorized collision occurred
     */
    static void detectCollisions(@NotNull List<@NotNull CollisionListener> items) throws CollisionException {
        var collisions = items.stream()
            .collect(Collectors.toMap(
                CollisionListener::getPosition,
                List::of,
                (c1, c2) -> StreamEx.of(c1).append(c2).collect(Collectors.toList())
            ))
            .values()
            .stream()
            .filter(list -> list.size() > 1)
            .toList();

        for (List<CollisionListener> collidingItems : collisions) {
            int size = collidingItems.size();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size && i != j; j++) {
                    collidingItems.get(i).onCollisionWith(collidingItems.get(j));
                    collidingItems.get(j).onCollisionWith(collidingItems.get(i));
                }
            }
        }
    }
}
