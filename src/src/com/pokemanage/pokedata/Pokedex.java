package com.pokemanage.pokedata;

import java.util.List;

public class Pokedex {
    private final List<String> uncapturedList;

    public Pokedex(final List<String> uncapturedList) {
        this.uncapturedList = uncapturedList;
    }

    public List<String> uncapturedList() {
        return this.uncapturedList;
    }

    public void capturePokemon(final String name) {
        uncapturedList.remove(name);
    }
}
