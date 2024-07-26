package fr.carbon.ewen.domain.utils.validation.rules;

import fr.carbon.ewen.domain.components.Explorer;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ReportAsSingleViolation
@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NoExplorerNameDuplicates.NoExplorerNameDuplicatesValidator.class)
@Documented
public @interface NoExplorerNameDuplicates {

    String message() default "Explorers must have unique names";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    /**
     * Checks if there are any duplicate explorer names in the given list.
     */
    class NoExplorerNameDuplicatesValidator implements ConstraintValidator<NoExplorerNameDuplicates, List<Explorer>> {

        @Override
        public boolean isValid(List<Explorer> explorers, ConstraintValidatorContext context) {
            Set<String> explorerNames = explorers.stream().map(Explorer::name).collect(Collectors.toSet());
            return explorerNames.size() == explorers.size();
        }
    }
}