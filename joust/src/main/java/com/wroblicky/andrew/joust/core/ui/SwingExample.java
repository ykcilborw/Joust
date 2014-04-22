package com.wroblicky.andrew.joust.core.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
 
public class SwingExample implements Runnable {
    
    public void run() {
        // Create the window
        JFrame f = new JFrame ("Hello, World!!");
        //JPanel panel = new JPanel();
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add a label and a button
        f.getContentPane().add(new JLabel("Hello, world!"));
        f.getContentPane().add(new JButton("Press me!"));
        //f.getContentPane().add(new JPanel());
        // arrange the components inside the window
        f.pack();
        //By default, the window is not visible. Make it visible.
        f.setVisible(true);
    }
 
    public static void main(String[] args) {
        SwingExample se = new SwingExample();
        // Schedules the application to be run at the correct time in the event queue.
        SwingUtilities.invokeLater(se);
    }
    
    public static void execute() {
    	SwingExample se = new SwingExample();
        // Schedules the application to be run at the correct time in the event queue.
        SwingUtilities.invokeLater(se);
    }
}