package fr.carbon.ewen.domain.utils.validation.rules;

import fr.carbon.ewen.domain.components.Explorer;
import fr.carbon.ewen.domain.components.Mountain;
import fr.carbon.ewen.domain.components.Treasure;
import fr.carbon.ewen.domain.general.Position;
import fr.carbon.ewen.domain.Simulation;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import one.util.streamex.StreamEx;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ReportAsSingleViolation
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NoPositionDuplicates.NoPositionDuplicatesValidator.class)
@Documented
public @interface NoPositionDuplicates {

    String message() default "Could not have position duplicates";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    /**
     * Checks if there are any duplicate positions in the given list.
     */
    class NoPositionDuplicatesValidator implements ConstraintValidator<NoPositionDuplicates, Simulation> {

        @Override
        public boolean isValid(@NotNull Simulation simulation, @NotNull ConstraintValidatorContext context) {

            List<Position> treasurePositions = simulation.getTreasures().stream().map(Treasure::getPosition).toList();
            List<Position> explorersPositions = simulation.getExplorers().stream().map(Explorer::getPosition).toList();
            List<Position> mountainsPositions = simulation.getMountains().stream().map(Mountain::position).toList();
            List<Position> treasureAndMountains =  StreamEx.<Position>empty().append(treasurePositions).append(mountainsPositions).toList();
            List<Position> explorerAndMountains =  StreamEx.<Position>empty().append(explorersPositions).append(mountainsPositions).toList();

            return
                doesNoContainsDuplicates(treasurePositions) &&
                doesNoContainsDuplicates(explorersPositions) &&
                doesNoContainsDuplicates(mountainsPositions) &&
                doesNoContainsDuplicates(treasureAndMountains) &&
                doesNoContainsDuplicates(explorerAndMountains);
        }

        private boolean doesNoContainsDuplicates(List<Position> positions) {
            return positions.size() == new HashSet<>(positions).size();
        }
    }
}