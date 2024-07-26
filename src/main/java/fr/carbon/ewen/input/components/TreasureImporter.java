package fr.carbon.ewen.input.components;


import fr.carbon.ewen.domain.components.Treasure;
import fr.carbon.ewen.domain.general.Position;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class TreasureImporter extends AImporter<Treasure> {

    public TreasureImporter() {
        super(Pattern.compile("T - (?<width>\\d+) - (?<height>\\d+) - (?<count>\\d+)"));
    }

    @Override
    public @NotNull Treasure tryImport(@NotNull Matcher match) {

        int width = Integer.parseInt(match.group("width"));
        int height = Integer.parseInt(match.group("height"));
        int count = Integer.parseInt(match.group("count"));

        return new Treasure(new Position(width, height), new Treasure.TreasureState(count));
    }
}
