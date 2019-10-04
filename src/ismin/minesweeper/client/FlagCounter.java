package ismin.minesweeper.client;

import javax.swing.*;
import java.awt.*;

public class FlagCounter extends JPanel {
    private final static int DIM_Y = 50;
    private final static int DIM_X = 200;
    private int counter = 0;
    private int operation = 0;
    private int nbMines = 0;

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        switch (operation) {
            case 0:
                counter = 0;
                break;
            case 1:
                counter += 1;
                break;
            case -1:
                counter += -1;
                break;
            default:
                break;
        }
        this.setBackground(new Color(189,195,199));
        gc.setColor(new Color(189,195,199));
        gc.fillRect(0, 0, DIM_X, DIM_Y);
        gc.setColor(new Color(44,62,80));
        gc.setFont(new Font("Nunito", Font.BOLD, 18));
        gc.drawString(counter + "/" + nbMines, getWidth()/3+getWidth()/8, getHeight()/2+getHeight()/8);
    }

    public void setOperation(int operation) {
        this.operation = operation;
        repaint();
    }

    public void setNbMines(int nbMines) {
        this.nbMines = nbMines;
    }
}
