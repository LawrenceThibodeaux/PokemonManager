package com.pokemanage.pokedata;

import java.util.PriorityQueue;

public class PokemonQueue {
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
}
