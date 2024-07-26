package fr.carbon.ewen.domain;

import fr.carbon.ewen.domain.components.*;
import fr.carbon.ewen.errors.ExplorerOutOfMapException;
import fr.carbon.ewen.errors.CollisionException;
import fr.carbon.ewen.utils.validation.rules.NoExplorerNameDuplicates;
import fr.carbon.ewen.utils.validation.rules.NoOutOfBoundPositions;
import fr.carbon.ewen.utils.validation.rules.NoPositionDuplicates;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;

import java.util.Comparator;
import java.util.List;

import static fr.carbon.ewen.domain.components.CollisionListener.detectCollisions;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Slf4j
public @NoPositionDuplicates @NoOutOfBoundPositions class Simulation {

    private final @NotNull @Valid PeruvianMap map;
    private final @NotNull List<@Valid Mountain> mountains;
    private final @NotNull List<@Valid Treasure> treasures;
    private final @NotNull @NoExplorerNameDuplicates List<@Valid Explorer> explorers;
    private boolean played = false;

    public void run() throws CollisionException, ExplorerOutOfMapException {

        if (played) throw new IllegalStateException("Simulation has already been run!");
        played = true;

        log.info("Starting simulation...");

        int maxSteps = explorers.stream()
            .map(explorer -> explorer.movements().size())
            .max(Comparator.comparing(v -> v))
            .orElse(0);

        List<CollisionListener> collisionListeners = StreamEx.<CollisionListener>empty()
            .append(mountains)
            .append(treasures)
            .append(explorers)
            .toList();

        for (int step = 0; step < maxSteps; step++) {
            log.info("Step {}:", step);

            for (Explorer explorer : explorers) {
                if (explorer.movements().size() <= step) continue;
                var movement = explorer.movements().get(step);

                switch (movement) {
                    case ROTATE_LEFT:
                        explorer.rotateLeft();
                        break;

                    case ROTATE_RIGHT:
                        explorer.rotateRight();
                        break;

                    case MOVE_FORWARD:
                        explorer.moveForward();
                        detectCollisions(collisionListeners);
                        if (!map.isInside(explorer.getPosition())) throw new ExplorerOutOfMapException(explorer);
                        break;
                }
            }
        }

        log.info("Simulation finished...");
    }
}
