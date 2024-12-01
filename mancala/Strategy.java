package mancala;

import java.awt.*;

public interface Strategy{
    void drawBoard(Graphics2D g2);
    
    void drawLabels(Graphics2D g2);

    void drawPits(Graphics2D g2, Model model);

    void drawMancalas(Graphics2D g2, Model model);
}