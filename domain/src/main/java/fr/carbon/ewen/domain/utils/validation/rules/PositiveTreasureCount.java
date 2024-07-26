package fr.carbon.ewen.domain.utils.validation.rules;

import fr.carbon.ewen.domain.components.Treasure;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ReportAsSingleViolation
@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PositiveTreasureCount.PositiveTreasureCountValidator.class)
@Documented
public @interface PositiveTreasureCount {

    String message() default "Treasure count should be positive";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};


    class PositiveTreasureCountValidator implements ConstraintValidator<PositiveTreasureCount, Treasure.TreasureState> {

        @Override
        public boolean isValid(Treasure.TreasureState state, ConstraintValidatorContext context) {
            return state.getCount() > 0;
        }
    }
}