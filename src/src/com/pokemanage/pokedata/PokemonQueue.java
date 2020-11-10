package com.pokemanage.pokedata;

import java.util.Collections;
import java.util.List;

/*
 * PokemonQueue: contains all pokemon not in current party.
 * Also keeps track of average pokemon level.
 */
public class PokemonQueue {
    private static final int MAX_BOX_SIZE = 20;
    private List<Pokemon> queue;
    private int[] boxSizes;

    public PokemonQueue(
            final List<Pokemon> queue,
            final int[] boxSizes) {
        this.queue = queue;
        this.boxSizes = boxSizes;
    }

    public int[] boxSizes() {
        return this.boxSizes;
    }

    public List<Pokemon> currentQueue() {
        return this.queue;
    }

    /*
     * Returns provided Pokemon to the queue,
     * and returns the box used.
     */
    public int enqueue(final Pokemon pokemon) {
        final int box = getSuggestedBox();
        pokemon.putInBox(box);
        boxSizes[box]++;
        queue.add(pokemon);
        Collections.sort(queue);
        return box;
    }

    public int enqueueInBox(final Pokemon pokemon, final int box) {
        if (boxSizes[box] >= MAX_BOX_SIZE) {
            System.err.println("Warning: A box is overfilled!");
        }

        pokemon.putInBox(box);
        boxSizes[box]++;
        queue.add(pokemon);
        Collections.sort(queue);
        return box;
    }

    public Pokemon dequeue() {
        final Pokemon onDeck = queue.remove(0);
        boxSizes[onDeck.box()]--;
        return onDeck;
    }

    public int getSuggestedBox() {
        for (int i = 1; i < boxSizes.length; i++) {
            if (boxSizes[i] < MAX_BOX_SIZE - 1) {
                return i;
            }
        }
        return 0;
    }
}
