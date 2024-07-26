package fr.carbon.ewen.domain.utils.validation;

import fr.carbon.ewen.domain.exceptions.SimulationValidationException;
import fr.carbon.ewen.domain.Simulation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

import java.util.Set;
import java.util.function.Function;

public class ValidatorService {
    private static <T> T validate(T item, Function<Set<ConstraintViolation<T>>, Exception> onError) throws Exception {
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