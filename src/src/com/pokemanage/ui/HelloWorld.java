package com.pokemanage.ui;

import java.awt.*;
import javax.swing.*;

public class HelloWorld {
    private static final Dimension STARTING_DIMENSION = new Dimension(800, 600);
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        PokemonManagerGUI p = new PokemonManagerGUI();
        frame.getContentPane().add(p.getMainPanel());

        //Display the window.
        frame.setPreferredSize(STARTING_DIMENSION);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
