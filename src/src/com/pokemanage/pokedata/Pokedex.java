package com.pokemanage.pokedata;

import java.util.Map;

public class Pokedex {
    private static final String CAPTURED = "CAPTURED";

    private final Map<String, String> possessionMap;

    public Pokedex(final Map<String, String> possessionMap) {
        this.possessionMap = possessionMap;
    }

    public Map<String, String> posessionMap() {
        return this.possessionMap;
    }

    public void capturePokemon(final String name) {
        possessionMap.put(name, CAPTURED);
    }
}
