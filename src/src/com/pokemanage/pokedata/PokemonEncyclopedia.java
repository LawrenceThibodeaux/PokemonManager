package com.pokemanage.pokedata;

import java.util.Map;

public class PokemonEncyclopedia {
    private final Map<String, PokemonData> nameToPokeDataMap;

    public PokemonEncyclopedia(
            final Map<String, PokemonData> nameToPokeDataMap
    ) {
        this.nameToPokeDataMap = nameToPokeDataMap;
    }

    public PokemonData getPokemonDataByName(final String name) {
        return nameToPokeDataMap.get(name);
    }
}
