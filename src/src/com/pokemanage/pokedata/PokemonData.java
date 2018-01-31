package com.pokemanage.pokedata;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PokemonData {
    private final String name;
    private final int number;
    private final Map<Integer, String> moveProgressByLevel;
    private final String lastMove;
    private final Set<PokemonVersionColor> availability;
    private final String evolutionCondition;
    private final List<String> evolutionTargetPokemon;
    private final PokeType type1;
    private final PokeType type2;

    public PokemonData(
            final String name,
            final int number,
            final Map<Integer, String> moveProgressByLevel,
            final Set<PokemonVersionColor> availability,
            final String evolutionCondition,
            final List<String> evolutionTargetPokemon,
            final PokeType type1,
            final PokeType type2
    ) {
        this.name = name;
        this.number = number;
        this.moveProgressByLevel = moveProgressByLevel;
        this.availability = availability;
        this.evolutionCondition = evolutionCondition;
        this.evolutionTargetPokemon = evolutionTargetPokemon;
        this.type1 = type1;
        this.type2 = type2;

        lastMove = calculateLastMove();
    }

    public String getNextMove(final int level) {
        if (level <= 0) {
            return null;
        }

        int nextMoveLevel = 101; // max level is 100
        for (Map.Entry<Integer, String> entry : moveProgressByLevel.entrySet()) {
            if (entry.getKey() > level && entry.getKey() < nextMoveLevel) {
                nextMoveLevel = entry.getKey();
            }
        }

        return moveProgressByLevel.get(nextMoveLevel) + "@" + nextMoveLevel;
    }

    public String getLastMove() {
        return this.lastMove;
    }

    private String calculateLastMove() {
        int maxMoveLevel = 0;
        for (Map.Entry<Integer, String> entry : moveProgressByLevel.entrySet()) {
            if (entry.getKey() > maxMoveLevel) {
                maxMoveLevel = entry.getKey();
            }
        }

        return moveProgressByLevel.get(maxMoveLevel) + "@" + maxMoveLevel;
    }
}
