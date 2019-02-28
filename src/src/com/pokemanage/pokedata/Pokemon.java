package com.pokemanage.pokedata;

import java.util.List;

public class Pokemon implements Comparable {
    public static final int OUT_OF_PC = 0;

    private String name;
    private String nickname;
    private int level;
    private int box;
    private int hp;
    private boolean isTraded;
    private List<HMMove> hmMoves;
    private PokemonVersionColor originalTrainer;

    public Pokemon(
            final String name,
            final String nickname,
            final int level,
            final int box,
            final int hp,
            final boolean isTraded,
            final List<HMMove> hmMoves,
            final PokemonVersionColor originalTrainer
    ){
        this.name = name;
        this.nickname = nickname;
        this.level = level;
        this.box = box;
        this.hp = hp;
        this.isTraded = isTraded;
        this.hmMoves = hmMoves;
        this.originalTrainer = originalTrainer;
    }

    public String name() {
        return this.name;
    }

    public String nickname() {
        return this.nickname;
    }

    public int level() {
        return this.level;
    }

    public int box() {
        return this.box;
    }

    public int hp() {
        return this.hp;
    }

    public boolean isTraded() {
        return this.isTraded;
    }

    public List<HMMove> hmMoves() {
        return this.hmMoves;
    }

    public PokemonVersionColor originalTrainer() {
        return this.originalTrainer;
    }

    public void levelUp() {
        this.level++;
    }

    public void putInBox(final int box) {
        this.box = box;
    }

    public void takeOutOfPc() {
        this.box = OUT_OF_PC;
    }

    @Override
    public int compareTo(final Object other) {
        final Pokemon o = (Pokemon) other;
        if (this.level() != o.level()) {
            return this.level() - o.level();
        } else if (this.hp() != o.hp()) {
            return this.hp() - o.hp();
        } else if (this.box() != o.box()) {
            return this.box() - o.box();
        }
        return this.name().compareTo(o.name());
    }

    @Override
    public boolean equals(final Object other) {
        final Pokemon o = (Pokemon) other;
        if (this.level() != o.level()) {
            return false;
        } else if (this.hp() != o.hp()) {
            return false;
        } else if (this.box() != o.box()) {
            return false;
        }
        return this.name().equals(o.name())
                && this.originalTrainer().equals(o.originalTrainer());
    }
}
