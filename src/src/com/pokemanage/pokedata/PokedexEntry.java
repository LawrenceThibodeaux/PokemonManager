package com.pokemanage.pokedata;

public class PokedexEntry {
    private final String name;
    private final String source;

    public PokedexEntry(final String name, final String source) {
        this.name = name;
        this.source = source;
    }

    public String name() {
        return this.name;
    }

    public String source() {
        return this.source;
    }

    public String toString() {
        return this.name + "," + this.source;
    }
}
