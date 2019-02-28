package com.pokemanage.fileio;

import com.pokemanage.pokedata.HMMove;
import com.pokemanage.pokedata.Pokemon;
import com.pokemanage.pokedata.PokemonQueue;
import com.pokemanage.pokedata.PokemonVersionColor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TrainerQueueFileManager {
    private static final String TRAINER_QUEUE_DATA_FILE_PREFIX =
            "C:\\Users\\lthibodeaux\\Documents\\PokemonManagerData\\TrainerData-";
    private static final String TEXT_SUFFIX = ".txt";
    private static final String FIELD_DELIMITER = ",";
    private static final String SUBFIELD_DELIMITER = ":";
    private static final int NUM_BOXES = 12;

    public TrainerQueueFileManager() {

    }

    public boolean saveQueue(final PokemonQueue queue, final PokemonVersionColor pvc) {
        // TODO: Overwrite the trainer queue file
        return true;
    }

    public PokemonQueue loadQueue(final PokemonVersionColor pvc) {
        final PriorityQueue<Pokemon> queue = new PriorityQueue<>();
        final int[] boxSizes = new int[NUM_BOXES + 1];
        final Path trainerQueue = Paths.get(
                TRAINER_QUEUE_DATA_FILE_PREFIX + pvc + TEXT_SUFFIX);

        double totalLevels = 0;
        try {
            final List<String> trainerEntries = Files.readAllLines(trainerQueue, StandardCharsets.UTF_8);
            for (String rawEntry : trainerEntries) {
                final String[] entryFields = rawEntry.split(FIELD_DELIMITER, 0);
                final Pokemon pokemon = parsePokemon(entryFields);
                queue.add(pokemon);
                boxSizes[pokemon.box()]++;
                totalLevels += pokemon.level();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PokemonQueue(queue, boxSizes, totalLevels / queue.size());
    }

    private Pokemon parsePokemon(final String[] entryFields) {
        return new Pokemon(
                entryFields[0],
                entryFields[1],
                Integer.parseInt(entryFields[2]),
                Integer.parseInt(entryFields[3]),
                Integer.parseInt(entryFields[4]),
                Boolean.valueOf(entryFields[5]),
                parseHMMoves(entryFields[6]),
                PokemonVersionColor.valueOf(entryFields[7])
        );
    }

    private List<HMMove> parseHMMoves(final String rawHMMoves) {
        final String[] subFields = rawHMMoves.split(SUBFIELD_DELIMITER, 0);
        final List<HMMove> result = new ArrayList<>();
        for (int i = 0; i < subFields.length; i ++) {
            result.add(HMMove.valueOf(subFields[i]));
        }
        return result;
    }
}
