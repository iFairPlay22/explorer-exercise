package fr.carbon.ewen.errors;

import fr.carbon.ewen.domain.components.CollisionListener;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CollisionException extends Exception {
    private final CollisionListener item1;
    private final CollisionListener item2;

    @Override
    public String getMessage() {
        return String.format(
            "An unexpected collision occurred between %s and %s",
            item1,
            item2
        );
    }
}
