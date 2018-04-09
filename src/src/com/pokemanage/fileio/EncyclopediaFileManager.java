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
                parseEvolutionTargets(entryFields[5]),
                PokeType.valueOf(entryFields[6]),
                PokeType.valueOf(entryFields[7])
        );
    }

    private Map<Integer, String> parseMoveLevelMap(final String rawField) {
        final String[] subFields = rawField.split(SUBFIELD_DELIMITER, 0);
        final Map<Integer, String> result = new HashMap<>();
        for (int i = 0; i < subFields.length; i += 2) {
            result.put(Integer.parseInt(subFields[i]), subFields[i+1]);
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

    private List<String> parseEvolutionTargets(final String rawField) {
        final String[] subFields = rawField.split(SUBFIELD_DELIMITER, 0);
        final List<String> result = new ArrayList<>();
        for (int i = 0; i < subFields.length; i ++) {
            result.add(subFields[i]);
        }
        return result;
    }
}
