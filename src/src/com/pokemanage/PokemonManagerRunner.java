package com.pokemanage;

import com.pokemanage.fileio.EncyclopediaFileManager;
import com.pokemanage.pokedata.PokemonData;
import com.pokemanage.pokedata.PokemonEncyclopedia;
import com.pokemanage.ui.PokemonManagerGUI;

import javax.swing.*;
import java.awt.*;

public class PokemonManagerRunner {
    private static final Dimension STARTING_DIMENSION = new Dimension(800, 600);

    private static PokemonEncyclopedia pokemonEncyclopedia;
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
        final EncyclopediaFileManager encyclopediaFileManager = new EncyclopediaFileManager();
        pokemonEncyclopedia = encyclopediaFileManager.load();
        final PokemonData bulbasaurData = pokemonEncyclopedia.getPokemonDataByName("bulbasaur");
        System.out.println(bulbasaurData);
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
