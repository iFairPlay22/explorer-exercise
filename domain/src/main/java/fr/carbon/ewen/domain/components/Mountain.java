package fr.carbon.ewen.domain.components;

import fr.carbon.ewen.domain.general.Position;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record Mountain(@NotNull @Valid Position position) {}