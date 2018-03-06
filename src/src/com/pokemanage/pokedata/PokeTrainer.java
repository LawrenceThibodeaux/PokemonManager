package com.pokemanage.pokedata;

import java.util.List;

public class PokeTrainer {
    private List<Pokemon> currentParty;
    private PokemonQueue pokeQueue;
    private Pokedex pokedex;
    private final PokemonVersionColor version;

    public PokeTrainer(
            final List<Pokemon> currentParty,
            final PokemonQueue pokeQueue,
            final Pokedex pokedex,
            final PokemonVersionColor version) {
        this.currentParty = currentParty;
        this.pokeQueue = pokeQueue;
        this.pokedex = pokedex;
        this.version = version;
    }

    public List<Pokemon> currentParty() {
        return this.currentParty;
    }

    public PokemonQueue pokeQueue() {
        return this.pokeQueue;
    }

    public Pokedex pokedex() {
        return this.pokedex;
    }

    public PokemonVersionColor version() {
        return this.version;
    }

    public void trade(final PokeTrainer other, final Pokemon toGive, final Pokemon toReceive) {
        if (!other.currentParty().contains(toReceive)) {
            return;
        }
        this.currentParty.remove(toGive);
        this.currentParty.add(toReceive);
        other.currentParty().remove(toReceive);
        other.currentParty().add(toGive);
    }

    public void addPokemon(final Pokemon pokemon) {
        this.pokeQueue.enqueue(pokemon);
        this.pokedex.capturePokemon(pokemon.name());
    }
}
