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
                renderPokeQueues();
                renderAvgLevel();
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
            System.out.println(trainer.pokeQueue().avgLevel());
        }
    }

    private static void renderParties() {
        for (final PokemonVersionColor color : PokemonVersionColor.values()) {

            final JTable partyTable = gui.getPartyTable(color);
            final DefaultTableModel model = (DefaultTableModel) partyTable.getModel();

            model.addColumn("Name");
            model.addColumn("Level");
            model.addColumn("HP");
            model.addColumn("Notes");

            partyTable.getColumnModel().getColumn(0).setMaxWidth(100);
            partyTable.getColumnModel().getColumn(1).setMaxWidth(50);
            partyTable.getColumnModel().getColumn(2).setMaxWidth(50);
            partyTable.getColumnModel().getColumn(2).setPreferredWidth(50);

            final PokeTrainer pt = trainers.get(color);
            for (final Pokemon p : pt.currentParty()) {
                model.addRow(new Object[]{p.name(), p.level(), p.hp(), p.getNotes(pokemonEncyclopedia)});
            }

            gui.getPartyTable(color).setModel(model);
            gui.getPartyTable(color).doLayout();
        }
    }

    private static void renderPokeQueues() {
        for (final PokemonVersionColor color : PokemonVersionColor.values()) {

            final JTable queueTable = gui.getQueueTable(color);
            final DefaultTableModel model = (DefaultTableModel) queueTable.getModel();

            model.addColumn("Name");
            model.addColumn("Level");
            model.addColumn("HP");
            model.addColumn("Notes");

            queueTable.getColumnModel().getColumn(0).setMaxWidth(100);
            queueTable.getColumnModel().getColumn(1).setMaxWidth(50);
            queueTable.getColumnModel().getColumn(2).setMaxWidth(50);
            queueTable.getColumnModel().getColumn(2).setPreferredWidth(50);

            final PokeTrainer pt = trainers.get(color);
            for (final Pokemon p : pt.pokeQueue().currentQueue()) {
                model.addRow(new Object[]{p.name(), p.level(), p.hp(), p.getNotes(pokemonEncyclopedia)});
            }

            gui.getQueueTable(color).setModel(model);
            gui.getQueueTable(color).doLayout();
        }
    }

    private static void renderAvgLevel() {
        for (final PokemonVersionColor color : PokemonVersionColor.values()) {

            final JLabel avgLevelValue = gui.getAvgLevelValue(color);

            final PokeTrainer pt = trainers.get(color);
            avgLevelValue.setText(String.format("%.2f", pt.pokeQueue().avgLevel()));
        }
    }
}
