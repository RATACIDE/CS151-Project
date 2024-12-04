package mancala;

import java.awt.*;
import java.awt.geom.*;

public class BlackAndWhite implements Strategy {
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 200;
    private static final int PIT_SIZE = 60;
    private static final int MANCALA_WIDTH = 70;
    private static final int MANCALA_HEIGHT = 160;
    
    // Define constants for the grid layout
    private static final int PIT_SPACING_X = 95;
    private static final int PIT_SPACING_Y = 70;
    private static final int START_X = 130;
    private static final int TOP_ROW_Y = 100 - (PIT_SPACING_Y / 2) - (PIT_SIZE / 2);
    private static final int BOTTOM_ROW_Y = 100 + (PIT_SPACING_Y / 2) - (PIT_SIZE / 2);

    // Draw the main board
    public void drawBoard(Graphics2D g2) {
        g2.setColor(Color.BLACK); // Set background to black
        g2.fill(new RoundRectangle2D.Double(90, 20, 620, 160, 20, 20)); // Rounded rectangle for board
    }

    // Draw the Mancala pits (A and B)
    public void drawMancalas(Graphics2D g2, Model model) {
        g2.setColor(Color.WHITE); // White for Mancalas
        // Mancala A
        g2.fill(new RoundRectangle2D.Double(20, 20, MANCALA_WIDTH, MANCALA_HEIGHT, 30, 30));
        // Mancala B
        g2.fill(new RoundRectangle2D.Double(710, 20, MANCALA_WIDTH, MANCALA_HEIGHT, 30, 30));
    }

    // Draw the individual pits for both rows
    public void drawPits(Graphics2D g2, Model model) {
        // Top row (B1-B6)
        for (int i = 0; i < 6; i++) {
            drawPit(g2, model.getCurrentBoardSpecific(i), START_X + (i * PIT_SPACING_X), TOP_ROW_Y);
        }

        // Bottom row (A1-A6)
        for (int i = 0; i < 6; i++) {
            drawPit(g2, model.getCurrentBoardSpecific(i + 6), START_X + (i * PIT_SPACING_X), BOTTOM_ROW_Y);
        }
    }

    // Helper method to draw a single pit with stones
    private void drawPit(Graphics2D g2, int numStones, int x, int y) {
        g2.setColor(Color.WHITE); // Pit color (white)
        g2.fill(new Ellipse2D.Double(x, y, PIT_SIZE, PIT_SIZE)); // Draw the pit

        g2.setColor(Color.BLACK); // Stone color (black)
        // Draw stones based on the number
        for (int j = 0; j < numStones; j++) {
            // Calculate a random position for the stone inside the pit
            int offsetX = (int) (Math.random() * (PIT_SIZE - 10));
            int offsetY = (int) (Math.random() * (PIT_SIZE - 10));
            g2.fill(new Ellipse2D.Double(x + offsetX, y + offsetY, 10, 10)); // Draw the stone
        }
    }

    // Draw labels for the pits
    public void drawLabels(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));

        // Label the Mancalas
        g2.drawString("B", 45, 100); // Mancala B label
        g2.drawString("A", 735, 100); // Mancala A label

        // Draw labels for the pits on both rows (B1-B6 and A1-A6)
        int startX = 150;
        int spacing = PIT_SPACING_X;
        int boardCenterY = 100;
        int labelSpacing = 70;

        int topLabelY = boardCenterY - (labelSpacing / 2) + 5;
        int bottomLabelY = boardCenterY + (labelSpacing / 2) + 5;

        // Top row (B6-B1)
        for (int i = 6; i >= 1; i--) {
            g2.drawString("B" + i, startX + ((6 - i) * spacing), topLabelY);
        }

        // Bottom row (A1-A6)
        for (int i = 1; i <= 6; i++) {
            g2.drawString("A" + i, startX + ((i - 1) * spacing), bottomLabelY);
        }
    }
}