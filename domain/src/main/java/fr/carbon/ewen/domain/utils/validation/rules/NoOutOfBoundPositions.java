package fr.carbon.ewen.domain.utils.validation.rules;

import fr.carbon.ewen.domain.components.Explorer;
import fr.carbon.ewen.domain.components.Mountain;
import fr.carbon.ewen.domain.components.Treasure;
import fr.carbon.ewen.domain.Simulation;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ReportAsSingleViolation;
import one.util.streamex.StreamEx;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ReportAsSingleViolation
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NoOutOfBoundPositions.NoPositionDuplicatesValidator.class)
@Documented
public @interface NoOutOfBoundPositions {

    String message() default "Out of bounds position";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};


    /**
     * Checks if all positions in the simulation are within the boundaries of the map.
     */
    class NoPositionDuplicatesValidator implements ConstraintValidator<NoOutOfBoundPositions, Simulation> {

        @Override
        public boolean isValid(Simulation simulation, ConstraintValidatorContext context) {
            int widthBoundary = simulation.getMap().maxWidth();
            int heightBoundary = simulation.getMap().maxHeight();

            return StreamEx.of(simulation.getMountains().stream().map(Mountain::position))
                .append(simulation.getTreasures().stream().map(Treasure::getPosition))
                .append(simulation.getExplorers().stream().map(Explorer::state).map(Explorer.ExplorerState::getPosition))
                .allMatch(position ->
                    0 <= position.width() && position.width() < widthBoundary
                        &&
                    0 <= position.height() && position.height() < heightBoundary
                );
        }
    }
}