package com.pokemanage.pokedata;

public class PokeTrainer {
    private Pokemon[] currentParty;
    private PokemonQueue pokeQueue;
    private Pokedex pokedex;
    private PokemonVersionColor version;

    public PokeTrainer(
            final Pokemon[] currentParty,
            final PokemonQueue pokeQueue,
            final Pokedex pokedex,
            final PokemonVersionColor version) {
        this.currentParty = currentParty;
        this.pokeQueue = pokeQueue;
        this.pokedex = pokedex;
        this.version = version;
    }
}
