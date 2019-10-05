package ismin.minesweeper.server;

import ismin.minesweeper.enums.Level;
import ismin.minesweeper.enums.ServerMessageTypes;
import ismin.minesweeper.utils.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class Server extends JFrame implements Runnable {

    private ServerGui serverGui;
    private ServerSocket serverSock;
    private Socket sock;
    private HashSet<EchoThread> clientThreadList = new HashSet<>();
    private HashMap<String, Integer> clientNameScoreMap;
    private Field serverField;
    private String[][] tabNames;
    private int nbRevealed;
    private int nbMineClicked;
    private boolean gameStarted;
    private boolean serverDown;

    /**
     * Server Constructor
     */
    Server() {
        super("Server");
        //iOS look
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

        serverGui = new ServerGui(this);

        setContentPane(serverGui);
        pack();
        setVisible(true);
        // Disconnect from server when window is closed
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                broadcast(ServerMessageTypes.SERVER_DISCONNECTION.value());
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Server main
     * @param args
     */
    public static void main(String[] args) {
        new Server();
    }

    public void startServer(int serverPort) {

        serverDown = false;
        serverGui.addMsg("Server started\n");
        serverGui.addMsg("Waiting for clients...\n");
        try {
            serverSock = new ServerSocket(serverPort);
            sock = null;
            new Thread(this).start();
        } catch (IOException e) {
            //serverGui.addMsg("\n\nSTART - ERROR: Error while opening server socket - shutting down the server");
            closeServer();
            e.printStackTrace();
        }
    }

    /**
     * Closes the server socket
     */
    void closeServer() {
        try {
            broadcast(ServerMessageTypes.SERVER_DISCONNECTION.value());
            clientThreadList.clear();
            gameStarted = false;
            serverDown = true;
            serverSock.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        EchoThread clientThread;
        while(!serverDown) {
            try {
                sock = serverSock.accept();
                new Thread(this).start();
                clientThread = new EchoThread(sock, this);
                clientThreadList.add(clientThread);
            } catch (IOException e) {
                //serverGui.addMsg("\n\nRUN - ERROR: Error while listening on socket - shutting down server");
                closeServer();
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts a new game for the server (and the client). The server Field is initialized here
     */
    public void startGame() {
        broadcast(ServerMessageTypes.MSG.value());
        broadcast("New game started ! \n");
        broadcast(ServerMessageTypes.START_GAME.value());
        if(serverGui.getLevelComboBox().getSelectedItem() == Level.EASY) {
            this.serverField = new Field(Level.EASY);
            tabNames = new String[Level.EASY.dimX][Level.EASY.dimY];
            broadcast(Level.EASY.toString());
            broadcast(Level.EASY.dimX);
            broadcast(Level.EASY.dimY);
        }
        else if(serverGui.getLevelComboBox().getSelectedItem() == Level.MEDIUM) {
            this.serverField = new Field(Level.MEDIUM);
            tabNames = new String[Level.MEDIUM.dimX][Level.MEDIUM.dimY];
            broadcast(Level.MEDIUM.toString());
            broadcast(Level.MEDIUM.dimX);
            broadcast(Level.MEDIUM.dimY);
        }
        else if(serverGui.getLevelComboBox().getSelectedItem() == Level.HARD) {
            this.serverField = new Field(Level.HARD);
            tabNames = new String[Level.HARD.dimX][Level.HARD.dimY];
            broadcast(Level.HARD.toString());
            broadcast(Level.HARD.dimX);
            broadcast(Level.HARD.dimY);
        }
        else if(serverGui.getLevelComboBox().getSelectedItem() == Level.CUSTOM) {
            this.serverField = new Field(Level.CUSTOM);
            tabNames = new String[Level.CUSTOM.dimX][Level.CUSTOM.dimY];
            broadcast(Level.CUSTOM.toString());
            broadcast(Level.CUSTOM.dimX);
            broadcast(Level.CUSTOM.dimY);
        }
        sendColorToPlayers();
        iniTab2D(tabNames, "none");
        nbRevealed = 0;
        storeAllNames();
        gameStarted = true;
        nbMineClicked = 0;
    }

    /**
     * Broadcast a <code>String</code> message
     * @param msg <code>String</code> message you want to broadcast
     */
    public void broadcast(String msg) {
        for (EchoThread echoThread : clientThreadList) {
            try {
                echoThread.getOutStream().writeUTF(msg);
            } catch (IOException e) {
                serverGui.addMsg("ERROR: Impossible to broadcast string message \n");
                e.printStackTrace();
            }
        }
    }

    /**
     * Broadcast a <code>int</code> message
     * @param msg <code>int</code> message you want to broadcast
     */
    public void broadcast(int msg) {
        for (EchoThread echoThread : clientThreadList) {
            try {https://www.javatpoint.com/java-collections-max-method
                echoThread.getOutStream().writeInt(msg);
            } catch (IOException e) {
                serverGui.addMsg("ERROR: Impossible to broadcast string message \n");
                e.printStackTrace();
            }
        }
    }

    /**
     * Broadcast a <code>boolean</code> message
     * @param msg <code>boolean</code> message you want to broadcast
     */
    public void broadcast(boolean msg) {
        for (EchoThread echoThread : clientThreadList) {
            try {
                echoThread.getOutStream().writeBoolean(msg);
            } catch (IOException e) {
                serverGui.addMsg("ERROR: Impossible to broadcast string message \n");
                e.printStackTrace();
            }
        }
    }

    public void checkAllClientConnected() {
        if(clientThreadList.isEmpty() && gameStarted) {
            JOptionPane.showMessageDialog(null, "All players logged out before the end of the game.\nThe game was stopped.", "All players logged out", JOptionPane.WARNING_MESSAGE);
            serverGui.addMsg("Game stopped\n");
            stopGame();
            serverGui.getStartButton().setText("Start Game");
        }
    }

    /**
     * Initializes a 2D <code>String</code> type array with a initial value
     * @param tab <code>String[][]</code> array you want to initialize
     * @param iniValue value that you want to initialize your array with.
     */
    private void iniTab2D(String[][] tab, String iniValue) {
        for(int i = 0; i < tab.length; i++) {
            for(int j = 0; j < tab[0].length; j++) {
                tab[i][j] = iniValue;
            }
        }
    }

    private void storeAllNames() {
        clientNameScoreMap = new HashMap<String, Integer>();
        for (EchoThread echoThread : clientThreadList) {
            clientNameScoreMap.put(echoThread.getClientName(), 0);
        }
    }

    /**
     * Broadcast the top 3 players to client in order for them to have direct scores
     */
    public void getDirectScores() {
        broadcast(ServerMessageTypes.DIRECT_SCORE.value());
        clientNameScoreMap.replaceAll((k, v) -> getScore(k));
        String player1 = ""; String player2 = ""; String player3 = "";
        HashMap<String, Integer> tmpHeshMap = (HashMap<String, Integer>) clientNameScoreMap.clone();
        String tmpMaxKeyStr = findMaxInHashMap(tmpHeshMap);
        player1 = "1: " + tmpMaxKeyStr + " (" + tmpHeshMap.get(tmpMaxKeyStr) + ")";
        tmpHeshMap.remove(tmpMaxKeyStr);
        if(clientNameScoreMap.size() > 1) {
            tmpMaxKeyStr = findMaxInHashMap(tmpHeshMap);
            player2 = "     2: " + tmpMaxKeyStr + " (" + tmpHeshMap.get(tmpMaxKeyStr) + ")";
            tmpHeshMap.remove(tmpMaxKeyStr);
            if(clientNameScoreMap.size() > 2) {
                tmpMaxKeyStr = findMaxInHashMap(tmpHeshMap);
                player3 = "     3: " + tmpMaxKeyStr + " (" + tmpHeshMap.get(tmpMaxKeyStr) + ")";
                tmpHeshMap.remove(tmpMaxKeyStr);
            }
        }
        String directScore = player1 + player2 + player3;
        broadcast(directScore);
    }

    private String findMaxInHashMap(HashMap<String, Integer> hashMap) {
        int maxValue = 0;
        String maxKey = "";
        for(String key : hashMap.keySet()) {
            if(hashMap.get(key) >= maxValue) {
                maxValue = hashMap.get(key);
                maxKey = key;
            }
        }
        return maxKey;
    }

    /**
     * Gets the score for each player which played a game
     */
    private String getAllScores() {
        clientNameScoreMap.replaceAll((k, v) -> getScore(k));
        String results = "Results: \n";
        for(String key : clientNameScoreMap.keySet()) {
            results = results.concat("  " + key + ": " + clientNameScoreMap.get(key) + "\n");
        }
        return results;
    }

    private int getScore(String name) {
        int score = 0;
        for(int i = 0; i<tabNames.length; i++) {
            for(int j = 0; j<tabNames[0].length; j++) {
                if(tabNames[i][j].equals(name)) {
                    score++;
                }
            }
        }
        return score;
    }

    public boolean isWin() {
        boolean win = serverField.getDimX()*serverField.getDimY()-nbRevealed == serverField.getNbMines();
        if(win) {
            String results = getAllScores();
            serverGui.addMsg("Game finished !\n" + results);
            broadcast(ServerMessageTypes.END_GAME.value());
            broadcast(results);
            serverGui.getStartButton().setText("Start Game");
            gameStarted = false;
        }
        return win;
    }

    public void stopGame() {
        broadcast(ServerMessageTypes.END_GAME.value());
        broadcast(getAllScores());
        serverGui.getStartButton().setText("Start Game");
        gameStarted = false;
    }

    public void checkAllClientLost() {
        if(nbMineClicked == clientThreadList.size()) {
            broadcast(ServerMessageTypes.MSG.value());
            broadcast("All players eliminated before the end of the game !\n");
            serverGui.addMsg("All players eliminated before the end of the game !\n");
            serverGui.addMsg("End of the game\n");
            stopGame();
        }
    }

    /**
     * Sends the player's color to each player in the game
     */
    private void sendColorToPlayers() {
        List<Integer> colorList = getColorList(clientThreadList.size());
        int i = 0;
        for(EchoThread client : clientThreadList) {
            try {
                client.setPlayerColor(colorList.get(i));
                client.getOutStream().writeInt(colorList.get(i));
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets a list of x colors, where x is the number of players
     * @param nbPlayers number of players in the game
     * @return a list of color in integer format
     */
    private List<Integer> getColorList(int nbPlayers) {
        List<Integer> colorList = new ArrayList<>();
        int offset = 255 / nbPlayers;
        colorList.add(new Color(offset, offset, offset).getRGB());
        for(int i = 1; i<nbPlayers; i++) {
            colorList.add(colorList.get(i-1) + offset);
        }
        return colorList;
    }

    /**
     * Getter for the server GUI
     * @return the server GUI
     */
    public ServerGui getServerGui() {
        return serverGui;
    }

    /**
     * Getter for the server client-thread HashSet
     * @return the server client-thread HashSet
     */
    public HashSet<EchoThread> getClientThreadList() {
        return clientThreadList;
    }

    /**
     * Getter for the server Field
     * @return the server field <code>serverField</code>
     */
    public Field getServerField() {
        return serverField;
    }

    /**
     * Getter for the array of names
     * @return the array of case-player names
     */
    public String[][] getTabNames() {
        return tabNames;
    }

    /**
     * Getter for the int indicating how many Cases were clicked
     * @return number of cases clicked
     */
    public int getNbRevealed() {
        return nbRevealed;
    }

    /**
     * Setter for the int indicating how many Cases were clicked
     * @param nbRevealed new number of cases clicked
     */
    public void setNbRevealed(int nbRevealed) {
        this.nbRevealed = nbRevealed;
    }

    /**
     * Getter for the <code>HashMap</code> that stores the client name (key) and its score (value)
     * @return <code>HashMap</code> object that stores the client name (key) and its score (value)
     */
    public HashMap<String, Integer> getClientNameScoreMap() {
        return clientNameScoreMap;
    }

    /**
     * Getter for the boolean that indicates if a game is already started by the server
     * @return <code>boolean</code> that indicates if a game is already started by the server
     */
    public boolean getGameStarted() {
        return gameStarted;
    }

    /**
     * Setter for the number of mines revealed by the clients
     * @param nbMineClicked new number of mines revealed
     */
    public void setNbMineClicked(int nbMineClicked) {
        this.nbMineClicked = nbMineClicked;
    }

    /**
     * Getter for the number of mines revealed by the clients
     * @return the number of mines revealed by the clients
     */
    public int getNbMineClicked() {
        return nbMineClicked;
    }
}
