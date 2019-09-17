package emse.ismin.minesweeper;

import javax.swing.*;

/**
 * This class is the main app class
 */
public class Minesweeper extends JFrame {

    private Field field;
    private boolean isStarted = false;
    private Gui gui;

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

    /**
     * Quits the app
     */
    public void quit() {
        System.exit(0);
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
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
