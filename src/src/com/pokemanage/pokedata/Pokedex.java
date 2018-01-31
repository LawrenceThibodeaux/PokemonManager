package com.pokemanage.pokedata;

import java.util.Map;

public class Pokedex {
    private final Map<String, Boolean> posessionMap;

    public Pokedex(final Map<String, Boolean> posessionMap) {
        this.posessionMap = posessionMap;
    }

    public Map<String, Boolean> posessionMap() {
        return this.posessionMap;
    }

    public void capturePokemon(final String name) {
        posessionMap.put(name, true);
    }
}
