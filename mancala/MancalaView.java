package mancala;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class MancalaView extends JPanel {
	private Strategy strat;
	private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 300;
	Model model;

	public MancalaView() {
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
	}

	public void setStrategy(Strategy pickedStrategy){
		strat = pickedStrategy;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		strat.drawBoard(g2);
		strat.drawMancalas(g2, model);
		strat.drawPits(g2, model);
		strat.drawLabels(g2);
	}
		
    public void stateChanged(ChangeEvent e) {
        repaint();
    }
}