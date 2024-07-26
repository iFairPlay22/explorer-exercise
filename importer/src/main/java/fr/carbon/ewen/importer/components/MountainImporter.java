package fr.carbon.ewen.importer.components;


import fr.carbon.ewen.domain.components.Mountain;
import fr.carbon.ewen.domain.general.Position;
import jakarta.validation.constraints.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MountainImporter extends AImporter<Mountain> {

    public MountainImporter() {
        super(Pattern.compile("M - (?<width>\\d+) - (?<height>\\d+)"));
    }

    @Override
    public @NotNull Mountain tryImport(@NotNull Matcher match) {

        int width =  Integer.parseInt(match.group("width"));
        int height =  Integer.parseInt(match.group("height"));

        return new Mountain(new Position(width, height));
    }
}
