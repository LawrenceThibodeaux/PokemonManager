package com.pokemanage.fileio;

import com.pokemanage.pokedata.PokeType;
import com.pokemanage.pokedata.PokemonData;
import com.pokemanage.pokedata.PokemonEncyclopedia;
import com.pokemanage.pokedata.PokemonVersionColor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class EncyclopediaFileManager {
    private static final String ENCYCLOPEDIA_DATA_FILE_PATH =
            "C:\\Users\\lthibodeaux\\Documents\\PokemonManagerData\\encyclopedia.txt";
    private static final String FIELD_DELIMITER = ",";
    private static final String SUBFIELD_DELIMITER = ":";

    public EncyclopediaFileManager() {

    }

    public PokemonEncyclopedia load() {
        final Map<String, PokemonData> nameToPokeDataMap = new HashMap<>();
        final Path encyclopedia = Paths.get(ENCYCLOPEDIA_DATA_FILE_PATH);
        try {
            final List<String> encyclopediaEntries = Files.readAllLines(encyclopedia, StandardCharsets.UTF_8);
            for (String rawEntry : encyclopediaEntries) {
                final String[] entryFields = rawEntry.split(FIELD_DELIMITER, 0);
                // We expect the first field to contain the Pokemon's name
                nameToPokeDataMap.put(entryFields[0], parsePokemonData(entryFields));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PokemonEncyclopedia(nameToPokeDataMap);
    }

    private PokemonData parsePokemonData(final String[] entryFields) {
        return new PokemonData(
                entryFields[0],
                Integer.parseInt(entryFields[1]),
                parseMoveLevelMap(entryFields[2]),
                parseVersionAvailibility(entryFields[3]),
                entryFields[4],
                entryFields[5],
                PokeType.valueOf(entryFields[6]),
                // At least one type, max of two
                (entryFields.length > 7) ? PokeType.valueOf(entryFields[7]) : null
        );
    }

    private Map<Integer, String> parseMoveLevelMap(final String rawField) {
        final String[] subFields = rawField.split(SUBFIELD_DELIMITER, 0);
        final Map<Integer, String> result = new HashMap<>();
        for (int i = 0; i < subFields.length; i += 3) {
            final Integer rbLevel = parseInt(subFields[i]);
            final Integer yLevel = parseInt(subFields[i+1]);
            final String moveName = subFields[i+2];
            // Assumes at least one level is non-null
            if (rbLevel == null) {
                result.put(yLevel, moveName);
            } else if (yLevel == null) {
                result.put(rbLevel, moveName + " (Y)");
            } else if (rbLevel.equals(yLevel)) {
                result.put(rbLevel, moveName);
            } else {
                result.put(rbLevel, moveName);
                result.put(yLevel, moveName + " (Y)");
            }
        }
        return result;
    }

    private Set<PokemonVersionColor> parseVersionAvailibility(final String rawField) {
        final String[] subFields = rawField.split(SUBFIELD_DELIMITER, 0);
        final Set<PokemonVersionColor> result = new HashSet<>();
        for (int i = 0; i < subFields.length; i ++) {
            result.add(PokemonVersionColor.valueOf(subFields[i]));
        }
        return result;
    }

    // For if we choose to model multiple evolution targets -- hopefully we can massage our data for this not to happen
    private List<String> parseEvolutionTargets(final String rawField) {
        if (rawField == null || rawField.isEmpty()) {
            return new ArrayList<>();
        }
        final String[] subFields = rawField.split(SUBFIELD_DELIMITER, 0);
        final List<String> result = new ArrayList<>();
        for (int i = 0; i < subFields.length; i ++) {
            result.add(subFields[i]);
        }
        return result;
    }

    private Integer parseInt(final String maybeInt) {
        try {
            return Integer.parseInt(maybeInt);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // For if our data contains multiple moves per level -- hopefully we can massage our data for this not to happen
    private void putOrAppend(final Map<Integer, String> result, final Integer level, final String move) {
        if (result.containsKey(level)) {
            final String moves = result.get(level);
            result.put(level, moves + ", " + move);
        } else {
            result.put(level, move);
        }
    }
}
