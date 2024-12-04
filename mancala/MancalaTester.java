package mancala;

import javax.swing.*;

public class MancalaTester {

    public static void main(String[] args) {
        // Create the model with 4 stones in each pit
        Model model = new Model(4);
        
        // Create the view
        MancalaView view = new MancalaView();
        
        // Create the controller and connect it to the view and model
        MancalaController controller = new MancalaController(model, view);
        
        // Set the view's model and controller
        view.setModel(model);
        view.setController(controller);
        
        // Set up the JFrame
        JFrame frame = new JFrame("Mancala Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);  // Center the window
        frame.setVisible(true);
    }
}