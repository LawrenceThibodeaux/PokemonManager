package com.pokemanage;

import com.pokemanage.fileio.EncyclopediaFileManager;
import com.pokemanage.fileio.TrainerPokedexFileManager;
import com.pokemanage.fileio.TrainerQueueFileManager;
import com.pokemanage.pokedata.PokeTrainer;
import com.pokemanage.pokedata.Pokemon;
import com.pokemanage.pokedata.PokemonEncyclopedia;
import com.pokemanage.pokedata.PokemonVersionColor;
import com.pokemanage.ui.PokemonManagerGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PokemonManagerRunner {
    private static final Dimension STARTING_DIMENSION = new Dimension(1600, 800);

    private static final EncyclopediaFileManager encyclopediaFileManager = new EncyclopediaFileManager();
    private static PokemonEncyclopedia pokemonEncyclopedia;
    private static final TrainerQueueFileManager trainerQueueFileManager = new TrainerQueueFileManager();
    private static final TrainerPokedexFileManager trainerPokedexFileManager = new TrainerPokedexFileManager();
    private static final Map<PokemonVersionColor, PokeTrainer> trainers = new HashMap<>();
    private static PokemonManagerGUI gui;
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("PokemonManager V0.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui = new PokemonManagerGUI();
        frame.getContentPane().add(gui.getMainPanel());

        //Display the window.
        frame.setPreferredSize(STARTING_DIMENSION);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        loadData();

        // TODO: hook up GUI elements to model
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();

                renderParties();
            }
        });
    }

    private static void loadData() {
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

    private static void renderParties() {
        for (final PokemonVersionColor color : PokemonVersionColor.values()) {

            final DefaultTableModel model = (DefaultTableModel) gui.getPartyTable(color).getModel();

            model.addColumn("Name");
            model.addColumn("Level");
            model.addColumn("HP");
            model.addColumn("Notes");

            final PokeTrainer pt = trainers.get(color);
            for (final Pokemon p : pt.currentParty()) {
                model.addRow(new Object[]{p.name(), p.level(), p.hp(), p.getNotes(pokemonEncyclopedia)});
            }

            gui.getPartyTable(color).setModel(model);
        }
    }
}
