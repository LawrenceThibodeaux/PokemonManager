package com.pokemanage;

import com.pokemanage.fileio.EncyclopediaFileManager;
import com.pokemanage.fileio.TrainerPokedexFileManager;
import com.pokemanage.fileio.TrainerQueueFileManager;
import com.pokemanage.pokedata.*;
import com.pokemanage.ui.PokemonManagerGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PokemonManagerRunner {
    private static final Dimension STARTING_DIMENSION = new Dimension(800, 600);

    private static final EncyclopediaFileManager encyclopediaFileManager = new EncyclopediaFileManager();
    private static PokemonEncyclopedia pokemonEncyclopedia;
    private static final TrainerQueueFileManager trainerQueueFileManager = new TrainerQueueFileManager();
    private static final TrainerPokedexFileManager trainerPokedexFileManager = new TrainerPokedexFileManager();
    private static final Map<PokemonVersionColor, PokeTrainer> trainers = new HashMap<>();
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("PokemonManager V0.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PokemonManagerGUI p = new PokemonManagerGUI();
        frame.getContentPane().add(p.getMainPanel());

        //Display the window.
        frame.setPreferredSize(STARTING_DIMENSION);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        init();

        // TODO: hook up GUI elements to model
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void init() {
        // 1. Load encyclopedia data
        pokemonEncyclopedia = encyclopediaFileManager.load();
        // 2. Create all three trainers
        // 3. Load queues for all trainers
        // 4. Load pokedex for all trainers
        for (final PokemonVersionColor color : PokemonVersionColor.values()) {
            final PokeTrainer trainer = new PokeTrainer(color);
            trainerQueueFileManager.loadQueue(trainer);
            trainerPokedexFileManager.loadPokedex(trainer);
            trainers.put(color, trainer);
        }
    }
}
