package emse.ismin.minesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class represents a case in the minesweeper field, and react to mouse clicks
 */
public class Case extends JPanel implements MouseListener {
    private final static int DIM = 50;
    private int x;
    private int y;
    private int nearbyMinesCount;
    private Minesweeper minesweeper;
    private boolean isClicked = false;
    private boolean rClick = false;
    private boolean isFlaged = false;

    /**
     * Constructor
     * @param minesweeper <code>Minesweeper</code> object to which the case should belongs
     * @param x column position of the case
     * @param y column position of the case
     */
    public Case(Minesweeper minesweeper, int x, int y) {
        this.minesweeper = minesweeper;
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(DIM, DIM));
        addMouseListener(this);
    }

    /**
     * Overrides <code>paintComponent</code>
     * @param gc <code>Graphics</code> object you want to paint
     */
    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        if (!isClicked) {
            if (rClick && !isFlaged) {
                isFlaged = true;
                drawAdaptativeImage(gc, "/img/flag.png");
                rClick = false;
            } else if (rClick) {
                isFlaged = false;
                rClick = false;
                drawAdaptativeImage(gc, "/img/tile.png");
            } else {
                drawAdaptativeImage(gc, "/img/tile.png");
            }
        } else {
            if (!isFlaged) {
                drawImageNumber(gc, nearbyMinesCount);
            } else {
                drawAdaptativeImage(gc, "/img/flag.png");
                isClicked = false;
            }
        }
    }

    private void drawImageNumber(Graphics gc, int nearByCount) {
        switch(nearByCount) {
            case -1:
                drawAdaptativeImage(gc, "/img/bomb.png");
                break;
            case 0:
                drawAdaptativeImage(gc, "/img/0.png");
                break;
            case 1:
                drawAdaptativeImage(gc, "/img/1.png");
                break;
            case 2:
                drawAdaptativeImage(gc, "/img/2.png");
                break;
            case 3:
                drawAdaptativeImage(gc, "/img/3.png");
                break;
            case 4:
                drawAdaptativeImage(gc, "/img/4.png");
                break;
            case 5:
                drawAdaptativeImage(gc, "/img/5.png");
                break;
            case 6:
                drawAdaptativeImage(gc, "/img/6.png");
                break;
            case 7:
                drawAdaptativeImage(gc, "/img/7.png");
                break;
            case 8:
                drawAdaptativeImage(gc, "/img/8.png");
                break;
        }
    };

    /**
     * Draws an image adapted to the case size
     * @param gc <code>Graphics</code> object on which you want to draw the image
     */
    private void drawAdaptativeImage(Graphics gc, String filePath) {
        try {
            //BufferedImage mineImage = ImageIO.read(new File(filePath));
            BufferedImage mineImage = ImageIO.read(getClass().getResourceAsStream(filePath));
            gc.drawImage(mineImage, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset a case
     */
    public void newCase() {
        isClicked = false;
        rClick = false;
        isFlaged = false;
        repaint();
    }

    /**
     * Handles mouse events
     * @param e <code>MouseEvent</code> that needs to be handled
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        rClick = SwingUtilities.isRightMouseButton(e);
        if(!rClick) {
            isClicked = true;
        }
        if(!minesweeper.getIsLost()) {
            nearbyMinesCount = minesweeper.getField().countNearbyMines(x, y);
            if (!minesweeper.getIsStarted()) {
                minesweeper.getGui().getCounter().restart();
                minesweeper.setIsStarted(true);
                minesweeper.setIsLost(false);
            }
            repaint();
            if(nearbyMinesCount == -1 && !rClick && !isFlaged) {
                minesweeper.getGui().getCounter().stop2();
                minesweeper.setIsLost(true);
                JOptionPane.showMessageDialog(null, "You suck !", "Try Again !", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
