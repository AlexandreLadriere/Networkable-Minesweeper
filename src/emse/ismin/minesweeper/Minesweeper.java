package emse.ismin.minesweeper;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

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
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage("/img/bomb.png"));


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
            saveScores();
        }
        return win;
    }

    /**
     * Saves the score in the score file
     */
    private void saveScores() {
        Path path = Paths.get(SCORE_FILENAME);
        String currentScoreStr = getField().getLevel().toString() + " " + getField().getLevel().dimX + " " + getField().getLevel().dimY + " " + getField().getLevel().nbMines + " " + getGui().getCounter().getCounterValue();
        String[] currentScoreTab = currentScoreStr.split(" ");
        if(!Files.exists(path)) {
            try {
                Files.write(path, (currentScoreStr + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(Files.exists(path)) {
            try {
                List<String> allLines = Files.readAllLines(path);
                if(isNewLevel(allLines, currentScoreTab)) {
                    allLines.add(currentScoreStr);
                }
                else {
                    for (int i = 0; i<allLines.size(); i++) {
                        String[] tmpStrTab = allLines.get(i).split(" ");
                        if (tmpStrTab[0].equals(getField().getLevel().toString()) &&
                                tmpStrTab[1].equals(String.valueOf(getField().getLevel().dimX)) &&
                                tmpStrTab[2].equals(String.valueOf(getField().getLevel().dimY)) &&
                                tmpStrTab[3].equals(String.valueOf(getField().getLevel().nbMines)) &&
                                Integer.parseInt(tmpStrTab[4]) > getGui().getCounter().getCounterValue()) {
                            allLines.set(i, currentScoreStr);
                        }
                    }
                }
                Files.write(path, "".getBytes());
                for(int i = 0; i<allLines.size(); i++) {
                    Files.write(path, (allLines.get(i)+"\n").getBytes(), StandardOpenOption.APPEND);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if the current score correspond to a new level or not
     * @param allLines all lines in the score file
     * @param currentScoreTab current score in string array format
     * @return a boolean that indicates if the level is already in the score file or not
     */
    private boolean isNewLevel(List<String> allLines, String[] currentScoreTab) {
        boolean isNew = true;
        if(allLines != null) {
            for (String line : allLines) {
                String[] tmpStrTab = line.split(" ");
                if (tmpStrTab[0].equals(currentScoreTab[0]) &&
                        tmpStrTab[1].equals(currentScoreTab[1]) &&
                        tmpStrTab[2].equals(currentScoreTab[2]) &&
                        tmpStrTab[3].equals(currentScoreTab[3])) {
                    isNew = false;
                    break;
                }
            }
        }
        return isNew;
    }

    /**
     * Transforms the information in the score file to a prettier list of string, ready to be displayed by the GUI
     * @return a list of string in which each string is a score written in a way that its ready to be displayed
     */
    public String getAllScoresToDisplay() {
        StringBuilder scores = new StringBuilder();
        Path path = Paths.get(SCORE_FILENAME);
        if(Files.exists(path)) {
            try {
                List<String> scoresInFile = Files.readAllLines(path);
                for (String s : scoresInFile) {
                    String[] tmpStrTab = s.split(" ");
                    scores.append(tmpStrTab[0]).append(" [").append(tmpStrTab[1]).append("x").append(tmpStrTab[2]).append(", ").append(tmpStrTab[3]).append("] = ").append(tmpStrTab[4]).append("\n\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scores.toString();
    }

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
