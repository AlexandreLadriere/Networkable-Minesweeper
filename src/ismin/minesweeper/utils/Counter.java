package ismin.minesweeper.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Implements a counter (in seconds)
 */
public class Counter extends JPanel implements Runnable {
    private final static int DIM_Y = 50;
    private final static int DIM_X = 150;
    private int counter = 0;
    private Thread counterProcess;

    public Counter() {
        setPreferredSize(new Dimension(DIM_X, DIM_Y));
    }

    @Override
    public void run() {
        while(counterProcess != null) {
            try {
                counterProcess.sleep(1000);
                counter+=1;
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        gc.setColor(new Color(189,195,199));
        gc.fillRect(0, 0, DIM_X, DIM_Y);
        gc.setColor(new Color(44,62,80));
        gc.setFont(new Font("Nunito", Font.BOLD, 18));
        gc.drawString("Timer: "+ counter, getWidth()/4, getHeight()/2+getHeight()/8);
    }

    /**
     * Restarts the thread
     */
    public void restart() {
        counter = 0;
        counterProcess = new Thread(this);
        counterProcess.start();
        repaint();
    }

    /**
     * Stops the counter without overriding the <code>stop()</code> method
     */
    public void stop2() {
        counterProcess = null;
    }

    /**
     * Getter for the counter value
     * @return counter value
     */
    public int getCounterValue() {
        return counter;
    }
}
