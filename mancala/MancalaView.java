package mancala;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MancalaView extends JPanel {
    private Model model;
    private MancalaController controller;
    private Strategy strat;
    
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 300;
    
    private JButton blackAndWhiteStrategy;
    private JButton americaStrategy;

    public MancalaView() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        
        // Strategy buttons
        blackAndWhiteStrategy = new JButton("Style 1");
        americaStrategy = new JButton("Style 2");

        // Add ActionListeners to handle button clicks for strategy
        blackAndWhiteStrategy.addActionListener(e -> setStrategy(new BlackAndWhite()));
        americaStrategy.addActionListener(e -> setStrategy(new America()));

        // Layout buttons for strategy
        setLayout(new FlowLayout());
        add(blackAndWhiteStrategy);
        add(americaStrategy);
    }

    // Set the model and controller for interaction
    public void setModel(Model model) {
        this.model = model;
    }

    public void setController(MancalaController controller) {
        this.controller = controller;
    }

    public void setStrategy(Strategy pickedStrategy) {
        strat = pickedStrategy;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // If strat is null, do nothing (or render a placeholder)
        if (strat != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Call the strategy methods to draw the board, mancala, pits, and labels
            strat.drawBoard(g2);
            strat.drawMancalas(g2, model);
            strat.drawPits(g2, model);
            strat.drawLabels(g2);
        } else {
            // Optionally, display a message or a placeholder if no strategy is selected yet
            g.drawString("Please select a style!", BOARD_WIDTH / 3, BOARD_HEIGHT / 2);
        }
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Add pit listeners
    public void addPitListener(ActionListener listener) {
        for (int i = 0; i < 14; i++) {
            JButton pitButton = new JButton("Pit " + i);
            pitButton.setActionCommand("Pit " + i);  // Set action command to "Pit 0", "Pit 1", etc.
            pitButton.addActionListener(listener);  // Attach listener to each pit button
            add(pitButton);  // Add button to the panel
        }
    }

    // Add undo button listener
    public void addUndoButtonListener(ActionListener listener) {
        // Create and add the undo button
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(listener);
        add(undoButton);  // Add the undo button to the view
    }

    public void displayWinner(char winner) {
        String winnerMessage = "";
        if (winner == 'A') {
            winnerMessage = "Player A Wins!";
        } else if (winner == 'B') {
            winnerMessage = "Player B Wins!";
        } else if (winner == 'C') {
            winnerMessage = "It's a Tie!";
        }

        JOptionPane.showMessageDialog(this, winnerMessage, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}