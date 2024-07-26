package fr.carbon.ewen.domain.components;

import fr.carbon.ewen.domain.general.Position;
import fr.carbon.ewen.domain.exceptions.CollisionException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record Mountain(@NotNull @Valid Position position) implements CollisionListener {

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void onCollisionWith(@NotNull CollisionListener item) throws CollisionException {
        switch (item) {
            case Explorer explorer:
                log.warn("{} was collected by explorer {}", this, explorer);
                throw new CollisionException(this, explorer);
            case Mountain mountain:
                log.warn("{} collided with mountain {}", this, mountain);
                throw new CollisionException(this, mountain);
            case Treasure treasure:
                log.warn("{} collided with treasure {}", this, treasure);
                throw new CollisionException(this, treasure);
        }
    }
}