package com.pokemanage.pokedata;

import java.util.List;

public class PokeTrainer {
    private List<Pokemon> currentParty;
    private PokemonQueue pokeQueue;
    private Pokedex pokedex;
    private final PokemonVersionColor version;
    public double avgLevelDividend;
    private String notes;

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

    public void sortParty() {
        this.currentParty.sort(new Pokemon.PokemonComparator());
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

    public void setTotalLevels(final double totalLevels) {
        this.avgLevelDividend = totalLevels;
    }

    public double avgLevel() {
        return this.avgLevelDividend / (pokeQueue.currentQueue().size() + currentParty.size());
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public String notes() {
        return this.notes;
    }

    // BUG: Trade does not update isTraded field
    public void trade(final PokeTrainer other, final Pokemon toGive, final Pokemon toReceive) {
        if (!other.currentParty().contains(toReceive)) {
            return;
        }
        this.currentParty.remove(toGive);
        this.currentParty.add(toReceive);
        other.currentParty().remove(toReceive);
        other.currentParty().add(toGive);
        this.avgLevelDividend += toReceive.level();
        this.avgLevelDividend -= toGive.level();
    }

    public void addPokemon(final Pokemon pokemon) {
        this.avgLevelDividend += pokemon.level();
        this.pokeQueue.enqueue(pokemon);
        this.pokedex.capturePokemon(pokemon.name());
    }
}
