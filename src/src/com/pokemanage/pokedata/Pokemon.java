package com.pokemanage.pokedata;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Pokemon implements Comparable {
    private static final String FIELD_DELIMITER = ",";
    private static final String SUBFIELD_DELIMITER = ":";
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

    public void levelUp(final int newHP) {
        this.level++;
        this.hp = newHP;
    }

    public void putInBox(final int box) {
        this.box = box;
    }

    public void evolve(final String targetPokemon) {
        this.name = targetPokemon;
    }

    public void takeOutOfPc() {
        this.box = OUT_OF_PC;
    }

    public void teachHMMove(final HMMove move) {
        hmMoves.add(move);
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

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.name()).append(FIELD_DELIMITER);
        sb.append(this.nickname()).append(FIELD_DELIMITER);
        sb.append(this.level()).append(FIELD_DELIMITER);
        sb.append(this.box()).append(FIELD_DELIMITER);
        sb.append(this.hp()).append(FIELD_DELIMITER);
        sb.append(this.isTraded()).append(FIELD_DELIMITER);
        sb.append(String.join(SUBFIELD_DELIMITER,
                this.hmMoves().stream()
                        .map(HMMove::toString)
                        .collect(Collectors.toList())))
                .append(FIELD_DELIMITER);
        sb.append(this.originalTrainer());
        return sb.toString();
    }

    public String getNotes(final PokemonEncyclopedia pe) {
        final PokemonData data = pe.getPokemonDataByName(this.name);
        return (this.isTraded ? "TR": "")
                + " | Next: " + data.getNextMove(this.level)
                + ((this.level <= data.lastMoveLevel()) ? " | Last: " + data.getLastMove() : "")
                + (!this.hmMoves().isEmpty() ? " | "
                + String.join(",", this.hmMoves().stream()
                        .map(HMMove::toString)
                        .collect(Collectors.toList())) : "");
    }

    public static class PokemonComparator implements Comparator<Pokemon> {
        public int compare(Pokemon a, Pokemon b)
        {
            return a.compareTo(b);
        }
    }
}
