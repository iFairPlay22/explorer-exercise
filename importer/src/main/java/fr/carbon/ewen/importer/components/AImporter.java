package fr.carbon.ewen.importer.components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public abstract class AImporter<T> {

    /**
     * The regular expression pattern used to parse the input string.
     */
    protected final @NotNull Pattern pattern;

    /**
     * The list of parsed items.
     */
    private final @NotNull @Valid List<@NotNull @Valid T> parsedItems = new ArrayList<T>();

    /**
     * Parses the input string and adds the parsed item to the parsed items.
     * @param str the input string to parse
     * @return the parsed item, encapsulated in an optional
     */
    public Optional<T> tryImport(@NotNull String str) {
        Matcher matcher =  pattern.matcher(str);

        if (matcher.matches()) {
            T item = tryImport(matcher);
            parsedItems.add(item);
            return Optional.of(item);
        }

        return Optional.empty();
    }

    /**
     * Tries to parse the input string and returns the parsed item.
     * @param match the matcher containing the parsed data
     * @return the parsed item
     */
    abstract T tryImport(@NotNull Matcher match);
}
