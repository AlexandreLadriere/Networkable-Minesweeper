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
    private JButton quitButton;
    private JButton newGameButton;
    private JMenuItem mQuit;
    private JMenuItem mInfo;
    private JMenuItem mLicense;
    private JMenuItem mNew;
    private JLabel difficultyLabel;
    private JRadioButtonMenuItem easyRadio;
    private JRadioButtonMenuItem mediumRadio;
    private JRadioButtonMenuItem hardRadio;
    private Case[][] tabCase;
    private Counter counter;

    /**
     * Creates a Gui according to the given <code>Minesweeper</code>
     * @param minesweeper <code>Minesweeper</code> you want to use
     */
    public Gui(Minesweeper minesweeper) {
        //main layout
        this.minesweeper = minesweeper;
        this.setLayout(new BorderLayout());
        setBackground(new Color(189,195,199));

        //menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu moreMenu = new JMenu("More...");
        JMenu difficultyMenu = new JMenu("Difficulty");
        menuBar.add(gameMenu);
        menuBar.add(Box.createGlue());
        menuBar.add(moreMenu);
            //"More..." menu
            mInfo = new JMenuItem("Info", KeyEvent.VK_I);
            mInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
            mInfo.setToolTipText("Software information");
            moreMenu.add(mInfo);
            mInfo.addActionListener(new Controller(this));
            mLicense = new JMenuItem("License", KeyEvent.VK_L);
            moreMenu.add(mLicense);
            mLicense.addActionListener(new Controller(this));
            mLicense.setToolTipText("License information");
            mLicense.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
            //"Game" menu
            gameMenu.add(difficultyMenu);
            mNew = new JMenuItem("New Game", KeyEvent.VK_N);
            gameMenu.add(mNew);
            mNew.addActionListener(new Controller(this));
            mNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
            mNew.setToolTipText("Restart a new game with the same difficulty");
            mQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
            gameMenu.add(mQuit);
            mQuit.addActionListener(new Controller(this));
            mQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));

            ButtonGroup bg = new ButtonGroup();
            easyRadio = new JRadioButtonMenuItem("Easy");
            mediumRadio = new JRadioButtonMenuItem("Medium");
            hardRadio = new JRadioButtonMenuItem("Hard");
            bg.add(easyRadio); bg.add(mediumRadio); bg.add(hardRadio);
            difficultyMenu.add(easyRadio); difficultyMenu.add(mediumRadio); difficultyMenu.add(hardRadio);
            checkDifficultyRadioButton();
            easyRadio.addActionListener(new Controller(this));
            mediumRadio.addActionListener(new Controller(this));
            hardRadio.addActionListener(new Controller(this));
        minesweeper.setJMenuBar(menuBar);

        //Labels for top panel of the main BorderLayout
        difficultyLabel = new JLabel("Level: "+minesweeper.getField().getLevel().toString());
        difficultyLabel.setForeground(new Color(44,62,80));
        difficultyLabel.setFont(new Font("Nunito", Font.BOLD, 18));
        difficultyLabel.setHorizontalAlignment(JLabel.CENTER);

        //Labels for bottom panel of the main BorderLayout
        quitButton = new JButton("Quit");
        quitButton.setForeground(new Color(44,62,80));
        quitButton.setFont(new Font("Nunito", Font.BOLD, 18));
        quitButton.addActionListener(new Controller(this));
        newGameButton = new JButton("New Game");
        newGameButton.setForeground(new Color(44,62,80));
        newGameButton.setFont(new Font("Nunito", Font.BOLD, 18));
        newGameButton.addActionListener(new Controller(this));

        //topPanel of the main BorderLayout
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(189,195,199));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(difficultyLabel, BorderLayout.WEST);
        counter = new Counter();
        topPanel.add(counter, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);

        //bottomPanel of the main BorderLayout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(newGameButton);
        bottomPanel.add(quitButton);
        bottomPanel.setBackground(new Color(189,195,199));
        this.add(bottomPanel, BorderLayout.SOUTH);

        //GridPanel with Field, in the center of the main BorderLayout
        gridPanel = new JPanel();
        gridPanel.setBackground(new Color(189,195,199));
        fillGridPanel(gridPanel);
        this.add(gridPanel, BorderLayout.CENTER);
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
        }
    }

    /**
     * Getter for the GUI counter
     * @return
     */
    public Counter getCounter() {
        return counter;
    }

    /**
     * Getter for <code>quitButton</code>
     * @return quitButton (<code>Button</code>)
     */
    public JButton getQuitButton() {
        return quitButton;
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
        difficultyLabel.setText("Level: "+level.toString());
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
    }
}

