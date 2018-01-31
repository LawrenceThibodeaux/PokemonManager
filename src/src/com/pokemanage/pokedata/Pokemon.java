package com.pokemanage.pokedata;

import java.util.List;

public class Pokemon {
    public static final int OUT_OF_PC = 0;

    private String name;
    private String nickname;
    private int level;
    private int box;
    private int hp;
    private boolean isTraded;
    private List<HMMove> hmMoves;
    private PokeTrainer originalTrainer;

    public Pokemon(
            final String name,
            final String nickname,
            final int level,
            final int box,
            final int hp,
            final boolean isTraded,
            final List<HMMove> hmMoves,
            final PokeTrainer originalTrainer
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

    public PokeTrainer originalTrainer() {
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
}
