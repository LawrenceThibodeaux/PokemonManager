package com.pokemanage.fileio;

import com.pokemanage.pokedata.PokeTrainer;
import com.pokemanage.pokedata.PokemonVersionColor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TrainerNotesFileManager {
    private static final String TRAINER_DEX_DATA_FILE_PREFIX =
            "C:\\Users\\lthibodeaux\\Documents\\PokemonManagerData\\TrainerNotes-";
    private static final String TEXT_SUFFIX = ".txt";

    public TrainerNotesFileManager() {

    }

    public boolean saveNotes(final String notes, final PokemonVersionColor pvc) {
        final Path trainerNotes = Paths.get(
                TRAINER_DEX_DATA_FILE_PREFIX + pvc + TEXT_SUFFIX);

        try {
            Files.write(trainerNotes, notes.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void loadNotes(final PokeTrainer trainer) {
        final Path trainerPokedex = Paths.get(
                TRAINER_DEX_DATA_FILE_PREFIX + trainer.version() + TEXT_SUFFIX);

        try {
            final String notes = new String ( Files.readAllBytes( trainerPokedex ) );
            trainer.setNotes(notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
