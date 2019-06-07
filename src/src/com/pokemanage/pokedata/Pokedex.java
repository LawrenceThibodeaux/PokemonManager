package com.pokemanage.pokedata;

import java.util.ArrayList;
import java.util.List;

public class Pokedex {
    private final List<PokedexEntry> uncapturedList;

    public Pokedex(final List<PokedexEntry> uncapturedList) {
        this.uncapturedList = uncapturedList;
    }

    public List<PokedexEntry> uncapturedList() {
        return this.uncapturedList;
    }

    public List<String> pokedexEntriesForStorage() {
        final List<String> result = new ArrayList<>();
        for (final PokedexEntry pe : this.uncapturedList) {
            result.add(pe.toString());
        }
        return result;
    }

    public void capturePokemon(final String name) {
        for (final PokedexEntry pe : this.uncapturedList) {
            if (name.equals(pe.name())) {
                this.uncapturedList.remove(pe);
                return;
            }
        }
    }
}
