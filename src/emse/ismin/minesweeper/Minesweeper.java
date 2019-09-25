package emse.ismin.minesweeper;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * This class is the main app class
 */
public class Minesweeper extends JFrame implements Runnable {

    private Field field;
    private boolean isStarted = false;
    private Gui gui;
    private boolean isLost = false;
    private int nbRevealed = 0;

    private Socket sock;
    private DataInputStream inStream;
    private DataOutputStream outStream;
    private String serverName;
    private String clientName;
    private int serverPort;
    private Thread process;

    /**
     * Creates the app
     */
    public Minesweeper() {
        super("Minesweeper - Alexandre Ladriere");

        //iOs look
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
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        new Minesweeper();
    }

    public void connectToServer() {
        serverName = gui.getServerNameTextField().getText();
        serverPort = Integer.parseInt(gui.getServerPortTextField().getText());
        clientName = gui.getClientNameTextField().getText();
        try {
            sock = new Socket(serverName, serverPort);
            process = new Thread(this);
            process.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            inStream = new DataInputStream(sock.getInputStream());
            outStream = new DataOutputStream(sock.getOutputStream());
            outStream.writeUTF(clientName);
            String connexionMsg = inStream.readUTF();
            gui.addMsg(connexionMsg + serverName + " (port: " + serverPort + ")\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(process != null) {
           /* try {
                //int cmd = inStream.readInt();
            if(cmd == Demineur.MSG) { // public static final int MSG = 0;
                String msg = inStream.readUTF();
                gui.addMsg(msg);
            }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        //lecture dans in
        //lecture de la commande
        // en fct de ce que je lis : j'affiche les mines/numeros/fin de partie
        //lecture du joueur qui a clické en x,y ...
    }

    /**
     * Disconnect the client from the server
     */
    public void DisconnectFromServer() {
        try {
            inStream.close();
            outStream.close();
            sock.close();
            gui.addMsg("Disconnected from the server: " + serverName + " (port: " + serverPort + ")\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tells if the game is win or not
     * @return a boolean indicating if the game is win or not
     */
    public boolean isWin() {
        boolean win = getField().getDimX()*getField().getDimY()-nbRevealed == getField().getNbMines();
        if(win) {
            saveScores();
            updateStats(true, getField().getLevel());
        }
        return win;
    }

    /**
     * Saves the score in the score file
     */
    private void saveScores() {
        Path path = Paths.get(FileNames.SCORE_FILENAME.toString());
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
                List<String> allLines = getAllLines(FileNames.SCORE_FILENAME.toString());
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
        Path path = Paths.get(FileNames.SCORE_FILENAME.toString());
        if(Files.exists(path)) {
            List<String> scoresInFile = getAllLines(FileNames.SCORE_FILENAME.toString());
            for (String s : scoresInFile) {
                String[] tmpStrTab = s.split(" ");
                scores.append(tmpStrTab[0]).append(" [").append(tmpStrTab[1]).append("x").append(tmpStrTab[2]).append(", ").append(tmpStrTab[3]).append("] = ").append(tmpStrTab[4]).append("\n\n");
            }
        }
        return scores.toString();
    }

    /**
     * Updates the statistics file
     * @param win boolean that indicates if the game was won or not
     * @param level level difficulty
     */
    public void updateStats(boolean win, Level level) {
        String eol = "\n";
        float played = 0, won = 0, loss = 0, easyPlayed = 0, easyWon = 0, mediumPlayed = 0, mediumWon = 0, hardPlayed = 0, hardWon = 0, customPlayed = 0, customWon = 0;
        float winRatio = 0, easyWinRatio = 0, mediumWinRatio = 0, hardWinRatio = 0, customWinRatio = 0;
        Path path = Paths.get(FileNames.STATS_FILENAME.toString());
        if (!Files.exists(path)) {
            try {
                Files.write(path, (played + eol + won + eol + loss + eol + winRatio + eol
                        + easyPlayed + eol + easyWon + eol + easyWinRatio + eol
                        + mediumPlayed + eol + mediumWon + eol + mediumWinRatio + eol
                        + hardPlayed + eol + hardWon + eol + hardWinRatio + eol
                        + customPlayed + eol + customWon + eol + customWinRatio).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> allLines = getAllLines(FileNames.STATS_FILENAME.toString());
        played = Float.parseFloat(allLines.get(0)); won = Float.parseFloat(allLines.get(1)); loss = Float.parseFloat(allLines.get(2)); winRatio = Float.parseFloat(allLines.get(3));
        easyPlayed = Float.parseFloat(allLines.get(4)); easyWon = Float.parseFloat(allLines.get(5)); easyWinRatio = Float.parseFloat(allLines.get(6));
        mediumPlayed = Float.parseFloat(allLines.get(7)); mediumWon = Float.parseFloat(allLines.get(8)); mediumWinRatio = Float.parseFloat(allLines.get(9));
        hardPlayed = Float.parseFloat(allLines.get(10)); hardWon = Float.parseFloat(allLines.get(11)); hardWinRatio = Float.parseFloat(allLines.get(12));
        customPlayed = Float.parseFloat(allLines.get(13)); customWon = Float.parseFloat(allLines.get(14));
        customWinRatio = Float.parseFloat(allLines.get(15));
        played += 1;
        if(win) {
            won += 1;
        }
        else {
            loss += 1;
        }
        winRatio = won / played;
        switch (level) {
            case EASY:
                easyPlayed += 1;
                if(win)
                    easyWon += 1;
                easyWinRatio = easyWon/easyPlayed;
                break;
            case MEDIUM:
                mediumPlayed += 1;
                if(win)
                    mediumWon += 1;
                mediumWinRatio = mediumWon/mediumPlayed;
                break;
            case HARD:
                hardPlayed += 1;
                if(win)
                    hardWon += 1;
                hardWinRatio = hardWon/hardPlayed;
                break;
            case CUSTOM:
                customPlayed += 1;
                if(win)
                    customWon += 1;
                customWinRatio = customWon/customPlayed;
                break;
        }
        try {
            Files.write(path, (played + eol + won + eol + loss + eol + winRatio + eol
                    + easyPlayed + eol + easyWon + eol + easyWinRatio + eol
                    + mediumPlayed + eol + mediumWon + eol + mediumWinRatio + eol
                    + hardPlayed + eol + hardWon + eol + hardWinRatio + eol
                    + customPlayed + eol + customWon + eol + customWinRatio).getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all the lines of a file in a <code>List<String></code> object
     * @param fileName filename of the file you want to get all the lines
     * @return String list of all the lines (one line = one element in the list)
     */
    public List<String> getAllLines(String fileName) {
        List<String> allLines = List.of();
        Path path = Paths.get(fileName);
        if(Files.exists(path)) {
            try {
                allLines = Files.readAllLines(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allLines;
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

    /**
     * Setter for the boolean indicating if the game was started or not
     * @param isStarted boolean indicating if the game was started or not
     */
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
