package mancala;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MancalaController {
    private Model model;
    private MancalaView view;

    public MancalaController(Model model, MancalaView view) {
        this.model = model;
        this.view = view;

        // Event listeners for pits and undo buttons
        view.addPitListener(new PitClickListener());
        view.addUndoButtonListener(new UndoButtonListener());
    }

    // Class to handle pit clicks
    private class PitClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String pitId = e.getActionCommand(); // Example: "A1", "B6"
            if (model.makeMove(pitId)) {
                // The model will notify the view when the move is made
            } else {
                view.showErrorMessage("Invalid move");
            }
        }
    }

    // Class that handles button clicks for undo
    private class UndoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.undo();
        }
    }
}
