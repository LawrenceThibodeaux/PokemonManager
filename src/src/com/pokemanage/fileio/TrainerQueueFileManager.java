package com.pokemanage.fileio;

import com.pokemanage.pokedata.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

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
        final Path trainerQueue = Paths.get(
                TRAINER_QUEUE_DATA_FILE_PREFIX + pvc + TEXT_SUFFIX);
        final List<String> lines = new LinkedList<>();
        for (final Pokemon p : queue.currentQueue()) {
            lines.add(p.toString());
            System.out.println(p.toString());
        }

        try {
            Files.write(trainerQueue, lines);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void loadQueue(final PokeTrainer trainer) {
        final PriorityQueue<Pokemon> queue = new PriorityQueue<>();
        final List<Pokemon> currentParty = new ArrayList<>();
        final int[] boxSizes = new int[NUM_BOXES + 1];
        final Path trainerQueue = Paths.get(
                TRAINER_QUEUE_DATA_FILE_PREFIX + trainer.version() + TEXT_SUFFIX);

        double totalLevels = 0;
        try {
            final List<String> trainerEntries = Files.readAllLines(trainerQueue, StandardCharsets.UTF_8);
            for (String rawEntry : trainerEntries) {
                final String[] entryFields = rawEntry.split(FIELD_DELIMITER, 0);
                final Pokemon pokemon = parsePokemon(entryFields);
                queue.add(pokemon);
                if (pokemon.box() == 0) {
                    currentParty.add(pokemon);
                }
                boxSizes[pokemon.box()]++;
                totalLevels += pokemon.level();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        trainer.setCurrentParty(currentParty);
        trainer.setPokeQueue(new PokemonQueue(queue, boxSizes, totalLevels / queue.size()));
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
        for (final String subField : subFields) {
            if (subField != null && !subField.equals("")) {
                result.add(HMMove.valueOf(subField));
            }
        }
        return result;
    }
}
