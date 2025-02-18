package fr.carbon.ewen.domain.exceptions;

import fr.carbon.ewen.domain.Simulation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class SimulationValidationException extends ValidationException {
    private final Set<ConstraintViolation<Simulation>> constraintViolations;

    @Override
    public String getMessage() {
        return String.format(
            "Validation failed for simulation: %s",
            constraintViolations
        );
    }
}