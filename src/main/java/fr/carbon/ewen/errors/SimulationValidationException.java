package fr.carbon.ewen.errors;

import fr.carbon.ewen.domain.Simulation;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class SimulationValidationException extends Exception{
    private final Set<ConstraintViolation<Simulation>> constraintViolations;

    @Override
    public String getMessage() {
        return String.format("Validation failed for simulation: %s", constraintViolations);
    }
}
