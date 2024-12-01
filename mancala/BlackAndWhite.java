package mancala;

import java.awt.*;
import java.awt.geom.*;


public class BlackAndWhite implements Strategy {
    private static final int BOARD_WIDTH = 800;
	private static final int BOARD_HEIGHT = 300;
	private static final int PIT_DIAMETER = 80;
	private static final int MANCALA_WIDTH = 80;
	private static final int MANCALA_HEIGHT = 200;

    public void drawBoard(Graphics2D g2){
        g2.setColor(Color.BLACK); // Black
		g2.fill(new RoundRectangle2D.Double(40, 50, BOARD_WIDTH - 80, BOARD_HEIGHT - 100, 20, 20));
    }

    public void drawMancalas(Graphics2D g2, Model model){
        g2.setColor(Color.WHITE); // White
		// Mancala A
		g2.fill(new RoundRectangle2D.Double(BOARD_WIDTH - 120, 30, MANCALA_WIDTH, MANCALA_HEIGHT, 20, 20));
		// Mancala B
		g2.fill(new RoundRectangle2D.Double(40, 30, MANCALA_WIDTH, MANCALA_HEIGHT, 20, 20));
    }

    public void drawPits(Graphics2D g2, Model model){
		int startX = 160;
		int topY = 80;
		int bottomY = 170;
		int num;
		// B1-B6 (top row)
		for (int i = 0; i < 6; i++) {
            g2.setColor(Color.WHITE);
			g2.fill(new Ellipse2D.Double(startX + (i * 100), topY, PIT_DIAMETER, PIT_DIAMETER));
            g2.setColor(Color.BLACK);
			num = model.getCurrentBoard(i) - 7;
			if(num == 1){
				g2.fill(new Ellipse2D.Double(startX + (i * 100), topY, 25, 25));
			}
			if(num == 2){
				g2.fill(new Ellipse2D.Double(startX-5 + (i * 100), topY, 25, 25));
				g2.fill(new Ellipse2D.Double(startX+5 + (i * 100), topY, 25, 25));
			}
			if(num == 3){
				g2.fill(new Ellipse2D.Double(startX-5 + (i * 100), topY-5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX+5 + (i * 100), topY-5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX + (i * 100), topY+5, 25, 25));
			}
			if(num == 4){
				g2.fill(new Ellipse2D.Double(startX-5 + (i * 100), topY-5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX+5 + (i * 100), topY-5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX-5 + (i * 100), topY+5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX+5 + (i * 100), topY+5, 25, 25));

			}
		}

		// A1-A6 (bottom row)
		for (int i = 0; i < 6; i++) {
            g2.setColor(Color.WHITE);
			g2.fill(new Ellipse2D.Double(startX + (i * 100), bottomY, PIT_DIAMETER, PIT_DIAMETER));
            g2.setColor(Color.BLACK);
			num = model.getCurrentBoard(i);
			if(num == 1){
				g2.fill(new Ellipse2D.Double(startX + (i * 100), bottomY, 25, 25));
			}
			if(num == 2){
				g2.fill(new Ellipse2D.Double(startX-5 + (i * 100), bottomY, 25, 25));
				g2.fill(new Ellipse2D.Double(startX+5 + (i * 100), bottomY, 25, 25));
			}
			if(num == 3){
				g2.fill(new Ellipse2D.Double(startX-5 + (i * 100), bottomY-5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX+5 + (i * 100), bottomY-5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX + (i * 100), bottomY+5, 25, 25));
			}
			if(num == 4){
				g2.fill(new Ellipse2D.Double(startX-5 + (i * 100), bottomY-5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX+5 + (i * 100), bottomY-5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX-5 + (i * 100), bottomY+5, 25, 25));
				g2.fill(new Ellipse2D.Double(startX+5 + (i * 100), bottomY+5, 25, 25));

			}
		}
    }

    public void drawLabels(Graphics2D g2){
        g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 14));
		// Label Mancalas
		g2.drawString("B", 70, 140);
		g2.drawString("A", BOARD_WIDTH - 90, 140);

		// Label pits
		int startX = 190;
		int topY = 120;
		int bottomY = 210;

		// Top row (B1-B6)
		for (int i = 1; i <= 6; i++) {
			g2.drawString("B" + (7 - i), startX + ((i - 1) * 100), topY);
		}
		// Bottom row (A1-A6)
		for (int i = 1; i <= 6; i++) {
			g2.drawString("A" + i, startX + ((i - 1) * 100), bottomY);
		}
    }
}