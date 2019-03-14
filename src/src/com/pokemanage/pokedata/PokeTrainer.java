package com.pokemanage.pokedata;

import java.util.List;

public class PokeTrainer {
    private List<Pokemon> currentParty;
    private PokemonQueue pokeQueue;
    private Pokedex pokedex;
    private final PokemonVersionColor version;

    public PokeTrainer(
            final PokemonVersionColor version) {
        this.version = version;
    }

    public List<Pokemon> currentParty() {
        return this.currentParty;
    }

    public void setCurrentParty(final List<Pokemon> currentParty) {
        this.currentParty = currentParty;
    }

    public PokemonQueue pokeQueue() {
        return this.pokeQueue;
    }

    public void setPokeQueue(final PokemonQueue pokeQueue) {
        this.pokeQueue = pokeQueue;
    }

    public Pokedex pokedex() {
        return this.pokedex;
    }

    public void setPokedex(final Pokedex pokedex) {
        this.pokedex = pokedex;
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
