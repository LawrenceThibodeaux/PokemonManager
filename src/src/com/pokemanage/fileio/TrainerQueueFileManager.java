package com.pokemanage.fileio;

import com.pokemanage.pokedata.Pokemon;
import com.pokemanage.pokedata.PokemonQueue;
import com.pokemanage.pokedata.PokemonVersionColor;

import java.util.PriorityQueue;

public class TrainerQueueFileManager {
    private static final String TRAINER_QUEUE_DATA_FILE_PATH_PREFIX =
            "C:\\Users\\lthibodeaux\\Documents\\PokemonManagerData\\TrainerQueue-";
    private static final String TEXT_FILE_SUFFIX = ".txt";
    private static final String FIELD_DELIMITER = ",";
    private static final int NUM_BOXES = 12;

    public TrainerQueueFileManager() {

    }

    public boolean saveQueue(final PokemonQueue queue, final PokemonVersionColor pvc) {
        // TODO: Overwrite the trainer queue file
        // Test
        return true;
    }

    public PokemonQueue loadQueue(final PokemonVersionColor pvc) {
        final PriorityQueue<Pokemon> queue = new PriorityQueue<>();
        final int[] boxSizes = new int[NUM_BOXES + 1];
        final double avgLevel = 0;
        // TODO: read the trainer queue file
        return new PokemonQueue(queue, boxSizes, avgLevel);
    }
}
