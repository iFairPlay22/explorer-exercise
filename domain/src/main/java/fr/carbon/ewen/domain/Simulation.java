package fr.carbon.ewen.domain;

import fr.carbon.ewen.domain.components.*;
import fr.carbon.ewen.domain.exceptions.CollisionException;
import fr.carbon.ewen.domain.general.Position;
import fr.carbon.ewen.domain.utils.validation.rules.NoExplorerNameDuplicates;
import fr.carbon.ewen.domain.utils.validation.rules.NoOutOfBoundPositions;
import fr.carbon.ewen.domain.utils.validation.rules.NoPositionDuplicates;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Slf4j
public @NoPositionDuplicates
@NoOutOfBoundPositions class Simulation {

    private final @NotNull @Valid PeruvianMap map;
    private final @NotNull List<@Valid Mountain> mountains;
    private final @NotNull List<@Valid Treasure> treasures;
    private final @NotNull @NoExplorerNameDuplicates List<@Valid Explorer> explorers;
    private boolean played = false;

    /**
     * Run the simulation.
     * @throws CollisionException if an explorer collides with another explorer or a mountain.
     */
    public void run() throws CollisionException {

        if (played) throw new IllegalStateException("Simulation has already been run!");
        played = true;

        log.info("Starting simulation...");

        CollisionListener.CollisionService collisionService = new CollisionListener.CollisionService(this);

        int maxSteps = explorers.stream()
            .map(explorer -> explorer.movements().size())
            .max(Comparator.comparing(v -> v))
            .orElse(0);

        for (int step = 0; step < maxSteps; step++) {
            log.info("Step {}:", step);

            for (Explorer explorer : explorers) {
                if (explorer.movements().size() <= step) continue;
                var movement = explorer.movements().get(step);

                switch (movement) {
                    case ROTATE_LEFT -> explorer.rotateLeft();
                    case ROTATE_RIGHT -> explorer.rotateRight();
                    case MOVE_FORWARD -> {
                        Position oldPosition = explorer.getPosition().copy();
                        explorer.moveForward(this);
                        collisionService.update(oldPosition, explorer);
                    }
                }
            }
        }

        log.info("Simulation finished...");
    }
}
