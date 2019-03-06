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
    private double avgLevel;

    public PokemonQueue(
            final PriorityQueue<Pokemon> queue,
            final int[] boxSizes,
            final double avgLevel) {
        this.queue = queue;
        this.boxSizes = boxSizes;
        this.avgLevel = avgLevel;
    }

    public int[] boxSizes() {
        return this.boxSizes;
    }

    public double avgLevel() {
        return this.avgLevel;
    }

    public PriorityQueue<Pokemon> currentQueue() {
        return this.queue;
    }

    /*
     * Returns provided Pokemon to the priority queue,
     * and returns the pokemon on the top of the queue.
     */
    public void enqueue(final Pokemon pokemon) {
        // TODO: recalculate average level
        int targetBox = 0;
        for (int i = 1; i < boxSizes.length; i++) {
            if (boxSizes[i] < MAX_BOX_SIZE) {
                pokemon.putInBox(i);
                boxSizes[i]++;
                queue.add(pokemon);
                return;
            }
        }
    }

    public Pokemon dequeue() {
        final Pokemon onDeck = queue.poll();
        boxSizes[onDeck.box()]--;
        // TODO: recalculate average level
        return onDeck;
    }
}
