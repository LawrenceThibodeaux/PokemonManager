package com.pokemanage;

import com.pokemanage.fileio.EncyclopediaFileManager;
import com.pokemanage.fileio.TrainerPokedexFileManager;
import com.pokemanage.fileio.TrainerQueueFileManager;
import com.pokemanage.pokedata.*;
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

        gui = new PokemonManagerGUI(trainers);
        frame.getContentPane().add(gui.getMainPanel());

        //Display the window.
        frame.setPreferredSize(STARTING_DIMENSION);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        loadData();

        // TODO: implement repaint methods
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();

                renderParties();
                renderPokeQueues();
                renderAvgLevel();
                renderPokedex();
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

    public static void renderParties() {
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
            partyTable.getColumnModel().getColumn(3).setPreferredWidth(50);

            final PokeTrainer pt = trainers.get(color);
            for (final Pokemon p : pt.currentParty()) {
                model.addRow(new Object[]{p.name(), p.level(), p.hp(), p.getNotes(pokemonEncyclopedia)});
            }

            gui.getPartyTable(color).setModel(model);
            gui.getPartyTable(color).doLayout();
        }
    }

    public static void repaintParties() {
        for (final PokemonVersionColor color : PokemonVersionColor.values()) {
            final JTable partyTable = gui.getPartyTable(color);
            final DefaultTableModel model = (DefaultTableModel) partyTable.getModel();

            model.setRowCount(0);

            final PokeTrainer pt = trainers.get(color);
            for (final Pokemon p : pt.currentParty()) {
                model.addRow(new Object[]{p.name(), p.level(), p.hp(), p.getNotes(pokemonEncyclopedia)});
            }

            gui.getPartyTable(color).setModel(model);
            gui.getPartyTable(color).doLayout();
        }
    }

    public static void renderPokeQueues() {
        for (final PokemonVersionColor color : PokemonVersionColor.values()) {

            final JTable queueTable = gui.getQueueTable(color);
            final DefaultTableModel model = (DefaultTableModel) queueTable.getModel();

            model.addColumn("Name");
            model.addColumn("Level");
            model.addColumn("HP");
            model.addColumn("Box");
            model.addColumn("Notes");

            queueTable.getColumnModel().getColumn(0).setMaxWidth(100);
            queueTable.getColumnModel().getColumn(1).setMaxWidth(50);
            queueTable.getColumnModel().getColumn(2).setMaxWidth(50);
            queueTable.getColumnModel().getColumn(3).setMaxWidth(50);
            queueTable.getColumnModel().getColumn(4).setPreferredWidth(50);

            final PokeTrainer pt = trainers.get(color);
            for (final Pokemon p : pt.pokeQueue().currentQueue()) {
                if (p.box() != 0) {
                    model.addRow(new Object[]{p.name(), p.level(), p.hp(), p.box(), p.getNotes(pokemonEncyclopedia)});
                }
            }

            gui.getQueueTable(color).setModel(model);
            gui.getQueueTable(color).doLayout();
        }
    }

    public static void renderAvgLevel() {
        for (final PokemonVersionColor color : PokemonVersionColor.values()) {

            final JLabel avgLevelValue = gui.getAvgLevelValue(color);

            final PokeTrainer pt = trainers.get(color);
            avgLevelValue.setText(String.format("%.2f", pt.pokeQueue().avgLevel()));
        }
    }

    public static void renderPokedex() {
        for (final PokemonVersionColor color : PokemonVersionColor.values()) {

            final JTable pokedexTable = gui.getPokedexTable(color);
            final DefaultTableModel model = (DefaultTableModel) pokedexTable.getModel();

            model.addColumn("Name");
            model.addColumn("Source");

            pokedexTable.getColumnModel().getColumn(0).setMaxWidth(100);
            pokedexTable.getColumnModel().getColumn(1).setPreferredWidth(50);

            final PokeTrainer pt = trainers.get(color);
            for (final PokedexEntry p : pt.pokedex().uncapturedList()) {
                model.addRow(new Object[]{p.name(), p.source()});
            }

            gui.getPokedexTable(color).setModel(model);
            gui.getPokedexTable(color).doLayout();
        }
    }
}
