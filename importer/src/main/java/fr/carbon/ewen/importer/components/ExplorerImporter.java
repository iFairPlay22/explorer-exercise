package fr.carbon.ewen.importer.components;


import fr.carbon.ewen.domain.components.Explorer;
import fr.carbon.ewen.domain.general.Orientation;
import fr.carbon.ewen.domain.general.Position;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class ExplorerImporter extends AImporter<Explorer> {

    public ExplorerImporter() {
        super(Pattern.compile("A - (?<name>\\w+) - (?<width>\\d+) - (?<height>\\d+) - (?<orientation>[NSEW]) - (?<actions>[AGD]+)"));
    }

    @Override
    public @NotNull Explorer tryImport(@NotNull Matcher match) {

        String name =  match.group("name");

        int initialWidth =  Integer.parseInt(match.group("width"));
        int initialHeight =  Integer.parseInt(match.group("height"));

        Orientation initialOrientation =  switch (match.group("orientation")) {
            case "N": yield Orientation.NORTH;
            case "S": yield Orientation.SOUTH;
            case "W": yield Orientation.WEST;
            case "E": yield Orientation.EAST;
            default: throw new IllegalStateException("Regexp error");
        };

        List<Explorer.ExplorerAction> actions = match.group("actions")
            .codePoints()
            .mapToObj(movement -> switch(movement) {
                case 'A' -> Explorer.ExplorerAction.MOVE_FORWARD;
                case 'G' -> Explorer.ExplorerAction.ROTATE_LEFT;
                case 'D' -> Explorer.ExplorerAction.ROTATE_RIGHT;
                default -> throw new IllegalStateException("Regexp error");
            })
            .collect(Collectors.toList());

        return new Explorer(name, actions, new Explorer.ExplorerState(new Position(initialWidth, initialHeight), initialOrientation, 0));
    }
}
