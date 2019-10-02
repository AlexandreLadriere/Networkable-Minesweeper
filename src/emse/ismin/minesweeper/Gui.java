package emse.ismin.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

/**
 * This class implements the GUI of the application
 */
public class Gui extends JPanel {

    private Minesweeper minesweeper;
    private JPanel gridPanel;
    private JPanel eastBorderLayout;
    private JButton connexionButton;
    private JButton quitButton;
    private JButton newGameButton;
    private JButton goOnlineButton;
    private JTextField serverNameTextField;
    private JTextField serverPortTextField;
    private JTextField clientNameTextField;
    private JTextField chatMsg;
    private JTextArea msgArea;
    private JLabel scoreDirectLabel;
    private JMenuItem mQuit;
    private JMenuItem mInfo;
    private JMenuItem mLicense;
    private JMenuItem mScores;
    private JMenuItem mStatistics;
    private JMenuItem mNew;
    private JLabel difficultyLabel;
    private JRadioButtonMenuItem easyRadio;
    private JRadioButtonMenuItem mediumRadio;
    private JRadioButtonMenuItem hardRadio;
    private JRadioButtonMenuItem customRadio;
    private Case[][] tabCase;
    private Counter counter;
    private FlagCounter flagCounter;

    private static final Color FOREGROUND_COLOR = new Color(44,62,80);
    private static final Color BACKGROUND_COLOR = new Color(189,195,199);
    private static final Color BACKGROUND_COLOR_2 = new Color(232,232,232);
    private static final Font BUTTON_FONT = new Font("Nunito", Font.BOLD, 18);

    /**
     * Creates a Gui according to the given <code>Minesweeper</code>
     * @param minesweeper <code>Minesweeper</code> you want to use
     */
    public Gui(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;

        //menu bar
        JMenuBar menuBar = new JMenuBar();
            //"More..." menu
            JMenu moreMenu = new JMenu("More...");
                //Info menu item
                mInfo = new JMenuItem("Info", KeyEvent.VK_I);
                mInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
                mInfo.setToolTipText("Software information");
                moreMenu.add(mInfo);
                mInfo.addActionListener(new Controller(this));
                //License menu item
                mLicense = new JMenuItem("License", KeyEvent.VK_L);
                moreMenu.add(mLicense);
                mLicense.addActionListener(new Controller(this));
                mLicense.setToolTipText("License information");
                mLicense.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
            //"Game" menu
            JMenu gameMenu = new JMenu("Game");
                //difficulty selection group
                JMenu difficultyMenu = new JMenu("Difficulty");
                gameMenu.add(difficultyMenu);
                ButtonGroup bg = new ButtonGroup();
                easyRadio = new JRadioButtonMenuItem("Easy");
                mediumRadio = new JRadioButtonMenuItem("Medium");
                hardRadio = new JRadioButtonMenuItem("Hard");
                customRadio = new JRadioButtonMenuItem("Custom");
                bg.add(easyRadio); bg.add(mediumRadio); bg.add(hardRadio); bg.add(customRadio);
                difficultyMenu.add(easyRadio); difficultyMenu.add(mediumRadio); difficultyMenu.add(hardRadio); difficultyMenu.add(customRadio);
                checkDifficultyRadioButton();
                easyRadio.addActionListener(new Controller(this));
                mediumRadio.addActionListener(new Controller(this));
                hardRadio.addActionListener(new Controller(this));
                customRadio.addActionListener(new Controller(this));
                //new Game menu item
                mNew = new JMenuItem("New Game", KeyEvent.VK_N);
                gameMenu.add(mNew);
                mNew.addActionListener(new Controller(this));
                mNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
                mNew.setToolTipText("Restart a new game with the same difficulty");
                //Best scores menu item
                mScores = new JMenuItem("Best Scores", KeyEvent.VK_B);
                gameMenu.add(mScores);
                mScores.addActionListener(new Controller(this));
                mScores.setToolTipText("Best scores by level");
                mScores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
                //Statistics menu item
                mStatistics = new JMenuItem("Statistics", KeyEvent.VK_S);
                gameMenu.add(mStatistics);
                mStatistics.addActionListener(new Controller(this));
                mStatistics.setToolTipText("Statistics about your games");
                mStatistics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
                //Quit menu item
                mQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
                gameMenu.add(mQuit);
                mQuit.addActionListener(new Controller(this));
                mQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        menuBar.add(gameMenu);
        menuBar.add(Box.createGlue());
        menuBar.add(moreMenu);
        minesweeper.setJMenuBar(menuBar);

        // Objects for NORTH panel of the main BorderLayout
        difficultyLabel = new JLabel("         Level: "+minesweeper.getField().getLevel().toString());
        difficultyLabel.setForeground(FOREGROUND_COLOR);
        difficultyLabel.setFont(BUTTON_FONT);
        difficultyLabel.setHorizontalAlignment(JLabel.CENTER);
        // Objects for SOUTH panel of the main BorderLayout
        quitButton = new JButton("Quit");
        quitButton.setForeground(FOREGROUND_COLOR);
        quitButton.setFont(BUTTON_FONT);
        quitButton.addActionListener(new Controller(this));
        newGameButton = new JButton("New Game");
        newGameButton.setForeground(FOREGROUND_COLOR);
        newGameButton.setFont(BUTTON_FONT);
        newGameButton.addActionListener(new Controller(this));
        goOnlineButton = new JButton("Go online");
        goOnlineButton.setForeground(FOREGROUND_COLOR);
        goOnlineButton.setFont(BUTTON_FONT);
        goOnlineButton.addActionListener(new Controller(this));

        // Objects for the SOUTH panel of the eastBorderLayout of the main BorderLayout
        serverNameTextField = new JTextField("localhost", 5);
        serverPortTextField = new JTextField("10000", 4);
        clientNameTextField = new JTextField("Your name", 6);
        connexionButton = new JButton("Connexion");
        connexionButton.setForeground(FOREGROUND_COLOR);
        connexionButton.setFont(BUTTON_FONT);
        connexionButton.addActionListener(new Controller(this));

        // Main BorderLayout
        this.setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        // NORTH of the main BorderLayout
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            topPanel.add(difficultyLabel, BorderLayout.WEST);
            flagCounter = new FlagCounter();
            topPanel.add(flagCounter, BorderLayout.CENTER);
            counter = new Counter();
            topPanel.add(counter, BorderLayout.EAST);
            topPanel.setBackground(BACKGROUND_COLOR);
        this.add(topPanel, BorderLayout.NORTH);
        // SOUTH of the main BorderLayout
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout());
            bottomPanel.add(newGameButton);
            bottomPanel.add(goOnlineButton);
            bottomPanel.add(quitButton);
            bottomPanel.setBackground(BACKGROUND_COLOR);
        this.add(bottomPanel, BorderLayout.SOUTH);
        // CENTER of the main BorderLayout
            gridPanel = new JPanel();
            gridPanel.setBackground(BACKGROUND_COLOR);
            fillGridPanel(gridPanel);
        this.add(gridPanel, BorderLayout.CENTER);

        // East BorderLayout of the main BorderLayout
        eastBorderLayout = new JPanel();
        eastBorderLayout.setLayout(new BorderLayout());
        eastBorderLayout.setVisible(false);
        // North
            JPanel topPanelBis = new JPanel();
            JLabel networkChatLabel = new JLabel("Network log", SwingConstants.CENTER);
            networkChatLabel.setForeground(FOREGROUND_COLOR);
            networkChatLabel.setFont(BUTTON_FONT);
            topPanelBis.add(networkChatLabel);
            topPanelBis.setBackground(BACKGROUND_COLOR_2);
            eastBorderLayout.add(topPanelBis, BorderLayout.NORTH);
        // Center
            // sub-center panel
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout());
                // sub-north
                msgArea = new JTextArea(18, 30);
                msgArea.setEditable(false);
                msgArea.setFont(new Font("Nunito", Font.PLAIN, 14));
                JScrollPane sp = new JScrollPane(msgArea);
                centerPanel.add(sp, BorderLayout.NORTH);
                // sub-center
                chatMsg = new JTextField();
                chatMsg.setFont(new Font("Nunito", Font.PLAIN, 14));
                chatMsg.addActionListener(new Controller(this));
                JScrollPane sp2 = new JScrollPane(chatMsg);
            centerPanel.add(sp2, BorderLayout.CENTER);
                // sub-south
                JPanel subSouthPanel  = new JPanel();
                subSouthPanel.setLayout(new BorderLayout());
                subSouthPanel.setBackground(BACKGROUND_COLOR_2);
                JLabel scoreDirect = new JLabel("  Scores: ");
                scoreDirect.setFont(BUTTON_FONT);
                scoreDirect.setBackground(BACKGROUND_COLOR_2);
                scoreDirectLabel = new JLabel("", SwingConstants.CENTER);
                scoreDirectLabel.setBackground(BACKGROUND_COLOR_2);
                scoreDirectLabel.setFont(new Font("Nunito", Font.PLAIN, 14));
                subSouthPanel.add(scoreDirect, BorderLayout.WEST);
                subSouthPanel.add(scoreDirectLabel, BorderLayout.CENTER);
            centerPanel.add(subSouthPanel, BorderLayout.SOUTH);
        eastBorderLayout.add(centerPanel, BorderLayout.CENTER);
        // South
            JPanel southPanelBis = new JPanel();
            southPanelBis.setLayout(new FlowLayout());
            southPanelBis.add(serverNameTextField);
            southPanelBis.add(serverPortTextField);
            southPanelBis.add(clientNameTextField);
            southPanelBis.add(connexionButton);
            southPanelBis.setBackground(BACKGROUND_COLOR_2);
            eastBorderLayout.add(southPanelBis, BorderLayout.SOUTH);
        this.add(eastBorderLayout, BorderLayout.EAST);

        flagCounter.setNbMines(minesweeper.getField().getNbMines());
    }

    /**
     * Sets the selected difficulty radio button <code>setSelected</code> parameters to <code>true</code>
     */
    private void checkDifficultyRadioButton() {
        switch(minesweeper.getField().getLevel()) {
            case EASY:
                easyRadio.setSelected(true);
                break;
            case MEDIUM:
                mediumRadio.setSelected(true);
                break;
            case HARD:
                hardRadio.setSelected(true);
                break;
            case CUSTOM:
                customRadio.setSelected(true);
                break;
        }
    }

    /**
     * Appends message to the client network log
     * @param msg message you want to add to the network log
     */
    public void addMsg(String msg) {
        msgArea.append(msg);
    }

    /**
     * Getter for the GUI counter
     * @return current Counter Object
     */
    public Counter getCounter() {
        return counter;
    }

    /**
     * Getter for the GUI flag counter
     * @return current FlagCounter Object
     */
    public FlagCounter getFlagCounter() {
        return flagCounter;
    }

    /**
     * Getter for <code>quitButton</code>
     * @return quitButton (<code>Button</code>)
     */
    public JButton getQuitButton() {
        return quitButton;
    }

    /**
     * Getter for the direct score label
     * @return <code>scoreDirectLabel</code>
     */
    public JLabel getScoreDirectLabel() {
        return scoreDirectLabel;
    }

    /**
     * Getter for the server name
     * @return serverNameTextField
     */
    public JTextField getServerNameTextField() {
        return serverNameTextField;
    }

    /**
     * Getter for the server port
     * @return serverPortTextField
     */
    public JTextField getServerPortTextField() {
        return serverPortTextField;
    }

    /**
     * Getter for the client name
     * @return clientNameTextField
     */
    public JTextField getClientNameTextField() {
        return clientNameTextField;
    }

    /**
     * Getter for the <code>Statistics</code> menu item
     * @return the Statistics menu item
     */
    public JMenuItem getmStatistics() {
        return mStatistics;
    }

    /**
     * Getter for the connexion button
     * @return connexionButton
     */
    public JButton getConnexionButton() {
        return connexionButton;
    }

    /**
     * Getter for <code>restartButton</code>
     * @return <code>restartButton</code>
     */
    public JButton getnewGameButton() {
        return newGameButton;
    }

    /**
     * Getter for the <code>Info</code> button
     * @return "Info" button
     */
    public JMenuItem getmInfo() {
        return mInfo;
    }

    /**
     * Getter for the <code>License</code> button
     * @return License button
     */
    public JMenuItem getmLicense() {
        return mLicense;
    }

    /**
     * Getter for the <code>Scores</code> button
     * @return Scores button
     */
    public JMenuItem getmScores() {
        return mScores;
    }

    /**
     * Getter for the <code>quit menu</code>item
     * @return quit menu item
     */
    public JMenuItem getmQuit() {
        return mQuit;
    }

    /**
     * Getter for the <code>Restart menu item</code>
     * @return <code>mRestart</code> menu item
     */
    public JMenuItem getmNew() {
        return mNew;
    }

    /**
     * Getter for the Easy radio button
     * @return <code>easyRadio</code> radio button
     */
    public JRadioButtonMenuItem getEasyRadio() {
        return easyRadio;
    }

    /**
     * Getter for the Hard radio button
     * @return <code>hardRadio</code> radio button
     */
    public JRadioButtonMenuItem getHardRadio() {
        return hardRadio;
    }

    /**
     * Getter for the Medium radio button
     * @return <code>mediumRadio</code> radio button
     */
    public JRadioButtonMenuItem getMediumRadio() {
        return mediumRadio;
    }

    /**
     * Getter for the Custom radio button
     * @return <code>customRadio</code> radio button
     */
    public JRadioButtonMenuItem getCustomRadio() {
        return customRadio;
    }

    /**
     * Getter for the chat message area
     * @return <code>chatMsg</code>
     */
    public JTextField getChatMsg() {
        return chatMsg;
    }

    /**
     * Getter for the gridpanel
     * @return the gridPanel
     */
    public JPanel getGridPanel() {
        return gridPanel;
    }

    /**
     * Getter for the difficulty Label
     * @return difficultyLabel
     */
    public JLabel getDifficultyLabel() {
        return difficultyLabel;
    }

    /**
     * Getter for the Minesweeper
     * @return <code>minesweeper</code>
     */
    public Minesweeper getMinesweeper() {
        return minesweeper;
    }

    /**
     * Getter for the array of cases
     * @return <code>tabCase</code> the array of cases
     */
    public Case[][] getTabCase() {
        return tabCase;
    }

    /**
     * Getter for the Online Panel
     * @return the Online panel
     */
    public JPanel getEastBorderLayout() {
        return eastBorderLayout;
    }

    /**
     * Getter for the go online button
     * @return the GoOnline button
     */
    public JButton getGoOnlineButton() {
        return goOnlineButton;
    }

    /**
     * Fills the grid panel with the case field
     * @param gridPanel <code>JPanel</code> object used to contain the case field
     */
    private void fillGridPanel(JPanel gridPanel) {
        gridPanel.setLayout(new GridLayout(minesweeper.getField().getDimX(), minesweeper.getField().getDimY()));
        tabCase = new Case[minesweeper.getField().getDimX()][minesweeper.getField().getDimY()];
        for(int i=0; i<minesweeper.getField().getDimX(); i++) {
            for(int j=0; j<minesweeper.getField().getDimY(); j++) {
                tabCase[i][j] = new Case(minesweeper, i, j);
                gridPanel.add(tabCase[i][j]);
            }
        }
    }

    /**
     * Fills the GUI Mine field according to the level of difficulty selected by the server
     * @param gridPanel gridPanel that need to be filled
     * @param dimX number of columns
     * @param dimY number of rows
     */
    public void fillGridPanelOnline(JPanel gridPanel, int dimX, int dimY) {
        gridPanel.setLayout(new GridLayout(dimX, dimY));
        tabCase = new Case[dimX][dimY];
        for(int i = 0; i<dimX; i++) {
            for(int j = 0; j<dimY; j++) {
                tabCase[i][j] = new Case(minesweeper, i, j);
                gridPanel.add(tabCase[i][j]);
            }
        }
    }

    /**
     * Restarts a new game with the same difficulty as the previous game
     */
    public void newGame() {
        minesweeper.getField().placeMines();
        for(int i = 0; i<minesweeper.getField().getDimX(); i++) {
            for(int j = 0; j<minesweeper.getField().getDimY(); j++) {
                tabCase[i][j].newCase();
            }
        }
        iniMinesweeper();
    }

    /**
     * Restarts a new game according to the specified difficulty level
     * @param level Difficulty level
     */
    public void newGame(Level level) {
        difficultyLabel.setText("         Level: "+level.toString());
        minesweeper.getField().newGame(level);
        gridPanel.removeAll();
        fillGridPanel(gridPanel);
        minesweeper.pack();
        iniMinesweeper();
    }

    /**
     * Initializes all the Minesweeper booleans and stop the counter
     */
    public void iniMinesweeper() {
        counter.stop2();
        minesweeper.setIsStarted(false);
        minesweeper.setIsLost(false);
        minesweeper.setNbRevealed(0);
        flagCounter.setNbMines(minesweeper.getField().getNbMines());
        flagCounter.setOperation(0);
    }
}

