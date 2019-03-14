package com.pokemanage.fileio;

import com.pokemanage.pokedata.Pokedex;
import com.pokemanage.pokedata.PokemonVersionColor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TrainerPokedexFileManager {
    private static final String TRAINER_DEX_DATA_FILE_PREFIX =
            "C:\\Users\\lthibodeaux\\Documents\\PokemonManagerData\\Pokedex-";
    private static final String TEXT_SUFFIX = ".txt";

    public TrainerPokedexFileManager() {

    }

    public boolean savePokedex(final Pokedex pokedex, final PokemonVersionColor pvc) {
        final Path trainerPokedex = Paths.get(
                TRAINER_DEX_DATA_FILE_PREFIX + pvc + TEXT_SUFFIX);

        try {
            Files.write(trainerPokedex, pokedex.uncapturedList());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Pokedex loadPokedex(final PokemonVersionColor pvc) {
        final Path trainerPokedex = Paths.get(
                TRAINER_DEX_DATA_FILE_PREFIX + pvc + TEXT_SUFFIX);

        try {
            final List<String> pokedexEntries = Files.readAllLines(trainerPokedex, StandardCharsets.UTF_8);
            return new Pokedex(pokedexEntries);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pokedex(new ArrayList<>());
    }
}
