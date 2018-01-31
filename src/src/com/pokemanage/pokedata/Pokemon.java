package com.pokemanage.pokedata;

import java.util.List;

public class Pokemon {
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
}
