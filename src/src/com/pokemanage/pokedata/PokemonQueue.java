package com.pokemanage.pokedata;

import java.util.PriorityQueue;

/*
 * PokemonQueue: contains all pokemon not in current party.
 * Also keeps track of average pokemon level.
 */
public class PokemonQueue {
    public static final int MAX_BOX_SIZE = 20;
    private PriorityQueue<Pokemon> queue;
    private int[] boxSizes;
    public double avgLevelDividend;

    public PokemonQueue(
            final PriorityQueue<Pokemon> queue,
            final int[] boxSizes,
            final double avgLevelDividend) {
        this.queue = queue;
        this.boxSizes = boxSizes;
        this.avgLevelDividend = avgLevelDividend;
    }

    public int[] boxSizes() {
        return this.boxSizes;
    }

    public double avgLevel() {
        return this.avgLevelDividend / queue.size();
    }

    public PriorityQueue<Pokemon> currentQueue() {
        return this.queue;
    }

    /*
     * Returns provided Pokemon to the priority queue,
     * and returns the pokemon on the top of the queue.
     */
    public int enqueue(final Pokemon pokemon) {
        for (int i = 1; i < boxSizes.length; i++) {
            if (boxSizes[i] < MAX_BOX_SIZE - 1) {
                pokemon.putInBox(i);
                boxSizes[i]++;
                queue.add(pokemon);
                return i;
            }
        }
        return 0;
    }

    public int enqueueInBox(final Pokemon pokemon, final int box) {
        if (boxSizes[box] >= MAX_BOX_SIZE) {
            throw new RuntimeException();
        }

        pokemon.putInBox(box);
        boxSizes[box]++;
        queue.add(pokemon);
        return box;
    }

    public Pokemon dequeue() {
        final Pokemon onDeck = queue.poll();
        boxSizes[onDeck.box()]--;
        return onDeck;
    }
}
