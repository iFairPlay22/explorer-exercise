package fr.carbon.ewen.input.components;


import fr.carbon.ewen.domain.components.PeruvianMap;
import fr.carbon.ewen.domain.general.Position;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeruvianMapImporter extends AImporter<PeruvianMap> {

    public PeruvianMapImporter() {
        super(Pattern.compile("C - (?<maxWidth>\\d+) - (?<maxHeight>\\d+)"));
    }

    @Override
    public @NotNull PeruvianMap tryImport(@NotNull Matcher match) {

        int maxWidth = Integer.parseInt(match.group("maxWidth"));
        int maxHeight = Integer.parseInt(match.group("maxHeight"));

        return new PeruvianMap(maxWidth, maxHeight);
    }
}
