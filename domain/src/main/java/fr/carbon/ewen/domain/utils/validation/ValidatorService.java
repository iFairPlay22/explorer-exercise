package fr.carbon.ewen.domain.utils.validation;

import fr.carbon.ewen.domain.exceptions.SimulationValidationException;
import fr.carbon.ewen.domain.Simulation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;

import java.util.Set;
import java.util.function.Function;

/**
 * Service for validating objects using Jakarta Bean Validation.
 */
public class ValidatorService {

    /**
     * Validates the given item and throws an exception if any validation errors are found.
     * @param item the item to validate.
     * @param onError a function that returns an validation exception to throw if there is a validation error.
     * @return
     * @param <T>
     * @throws Exception
     */
    private static <T> T validate(T item, Function<Set<ConstraintViolation<T>>, ValidationException> onError) throws ValidationException {
        try (var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            var validationErrors = validatorFactory.getValidator().validate(item);
            if (!validationErrors.isEmpty()) {
                throw onError.apply(validationErrors);
            }
        }
        return item;
    }

    public static Simulation validate(Simulation simulation) throws Exception {
        return validate(simulation, SimulationValidationException::new);
    }
}