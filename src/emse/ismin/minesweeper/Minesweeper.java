package emse.ismin.minesweeper;

import javax.swing.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is the main app class
 */
public class Minesweeper extends JFrame {

    private static final String SCORE_FILENAME = "scores.dat";
    private Field field;
    private boolean isStarted = false;
    private Gui gui;
    private boolean isLost = false;
    private int nbRevealed = 0;

    /**
     * Creates the app
     */
    public Minesweeper() {
        super("Minesweeper - Alexandre Ladriere");


        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WikiTeX");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        this.field = new Field(Level.EASY);
        gui = new Gui(this);
        setContentPane(gui);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * Setter for the boolean indicating if the game was started or not
     * @param isStarted boolean indicating if the game was started or not
     */

    /**
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        new Minesweeper();
    }

    public boolean isWin() {
        boolean win = getField().getDimX()*getField().getDimY()-nbRevealed == getField().getNbMines();
        if(win) {
            //saveScores();
        }
        return win;
    }

    /*
    private void saveScores() {
        Path path = Paths.get(SCORE_FILENAME);
        if(!Files.exists(path)) {

        }
    }
     */

    /**
     * Quits the app
     */
    public void quit() {
        System.exit(0);
    }

    /**
     * Setter for the number of case revealed by the player
     * @param nbRevealed number of case revealed by the user
     */
    public void setNbRevealed(int nbRevealed) {
        this.nbRevealed = nbRevealed;
    }

    /**
     * Getter for the number of cases revealed by the player
     * @return the number of cases revealed by the user
     */
    public int getNbRevealed() {
        return nbRevealed;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    /**
     * Getter for the boolean which indicates if the game is lost or not
     * @return the boolean which indicates if the game is lost or not
     */
    public boolean getIsLost() {
        return isLost;
    }

    /**
     * Setter for the boolean which indicates if the game is lost or not
     * @param isLost boolean which indicates if the game is lost or not
     */
    public void setIsLost(boolean isLost) {
        this.isLost = isLost;
    }

    /**
     * Getter for the boolean indicating if the game was started or not
     * @return boolean indicating if the game was started or not
     */
    public boolean getIsStarted() {
        return isStarted;
    }

    /**
     * Getter for the minesweeper GUI
     * @return GUI
     */
    public Gui getGui() {
        return gui;
    }

    /**
     * Getter for the Champ
     * @return champ of the app
     */
    public Field getField() {
        return field;
    }

    /**
     * Setter for the Field
     * @param field
     */
    public void setField(Field field) {
        this.field = field;
    }
}
