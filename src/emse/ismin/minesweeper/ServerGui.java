package emse.ismin.minesweeper;

import javax.swing.*;
import java.awt.*;

public class ServerGui extends JPanel {

    private Server server;
    private JButton startButton;
    private JButton startServerButton;
    private JTextArea msgArea;
    private JTextField serverPort;
    private JTextField serverName;
    private JRadioButtonMenuItem easyRadio;
    private JRadioButtonMenuItem mediumRadio;
    private JRadioButtonMenuItem hardRadio;

    private static final Color FOREGROUND_COLOR = new Color(44,62,80);
    private static final Color BACKGROUND_COLOR = new Color(189,195,199);
    private static final Font BUTTON_FONT = new Font("Nunito", Font.BOLD, 18);
    private static final Font RADIO_BUTTON_FONT = new Font("Nunito", Font.BOLD, 14);

    public ServerGui(Server server) {
        this.server = server;

        //this.setPreferredSize(new Dimension(300, 400));
        this.setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // NORTH panel of the main BorderLayout
        JLabel titleLabel = new JLabel("Minesweeper server");
            titleLabel.setForeground(FOREGROUND_COLOR);
            titleLabel.setFont(BUTTON_FONT);
        JPanel topPanel = new JPanel();
            topPanel.setLayout(new FlowLayout());
            topPanel.add(titleLabel);
            topPanel.setBackground(BACKGROUND_COLOR);
        this.add(topPanel, BorderLayout.NORTH);

        // CENTER panel of the main BorderLayout
        msgArea = new JTextArea(18, 25);
        msgArea.setEditable(false);
        msgArea.setFont(new Font("Nunito", Font.PLAIN, 14));
        JScrollPane sp = new JScrollPane(msgArea);
        this.add(sp, BorderLayout.CENTER);

        // SOUTH panel of the main BorderLayout
        startButton = new JButton("Start Game");
        startButton.setForeground(FOREGROUND_COLOR);
        startButton.setFont(BUTTON_FONT);
        startButton.addActionListener(new ServerController(this));
        startButton.setEnabled(false);

        startServerButton = new JButton("Start Server");
        startServerButton.setForeground(FOREGROUND_COLOR);
        startServerButton.setFont(BUTTON_FONT);
        startServerButton.addActionListener(new ServerController(this));

        JPanel southPanel = new JPanel();
            southPanel.setLayout(new BorderLayout());
        JPanel northPanelBis = new JPanel();
            ButtonGroup group = new ButtonGroup();
            northPanelBis.setLayout(new FlowLayout());
            northPanelBis.setBackground(BACKGROUND_COLOR);
            easyRadio = new JRadioButtonMenuItem("Easy");
            easyRadio.setSelected(true);
            easyRadio.setForeground(FOREGROUND_COLOR);
            easyRadio.setFont(RADIO_BUTTON_FONT);
            group.add(easyRadio);
            mediumRadio = new JRadioButtonMenuItem("Medium");
            mediumRadio.setForeground(FOREGROUND_COLOR);
            mediumRadio.setFont(RADIO_BUTTON_FONT);
            group.add(mediumRadio);
            hardRadio = new JRadioButtonMenuItem("Hard");
            hardRadio.setForeground(FOREGROUND_COLOR);
            hardRadio.setFont(RADIO_BUTTON_FONT);
            group.add(hardRadio);
        northPanelBis.add(easyRadio); northPanelBis.add(mediumRadio); northPanelBis.add(hardRadio);
        JPanel centerPanelBis = new JPanel();
            centerPanelBis.setLayout(new FlowLayout());
            centerPanelBis.setBackground(BACKGROUND_COLOR);
            serverPort = new JTextField("10000", 9);
            serverName = new JTextField("localhost", 9);
            centerPanelBis.add(serverName);
            centerPanelBis.add(serverPort);
        JPanel southPanelBis = new JPanel();
            southPanelBis.setLayout(new FlowLayout());
            southPanelBis.setBackground(BACKGROUND_COLOR);
            southPanelBis.add(startServerButton);
            southPanelBis.add(startButton);
        southPanel.add(northPanelBis, BorderLayout.NORTH);
        southPanel.add(centerPanelBis, BorderLayout.CENTER);
        southPanel.add(southPanelBis, BorderLayout.SOUTH);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Appends a message to the text area of the server gui
     * @param msg msg you want to appened to the text area
     */
    public void addMsg(String msg) {
        msgArea.append(msg);
    }

    /**
     * Getter for the server start button
     * @return startButton Button
     */
    public JButton getStartButton() {
        return startButton;
    }

    /**
     * Getter for the gui server
     * @return Server object associated to this GUI
     */
    public Server getServer() {
        return server;
    }

    /**
     * Getter for the EASY level radio button
     * @return the easyRadioButton value
     */
    public JRadioButtonMenuItem getEasyRadio() {
        return easyRadio;
    }

    /**
     * Getter for the MEDIUM level radio button
     * @return the mediumRadioButton value
     */
    public JRadioButtonMenuItem getMediumRadio() {
        return mediumRadio;
    }

    /**
     * Getter for the HARD level radio button
     * @return the hardRadioButton value
     */
    public JRadioButtonMenuItem getHardRadio() {
        return hardRadio;
    }

    /**
     * Getter for the start server button
     * @return the start server button
     */
    public JButton getStartServerButton() {
        return startServerButton;
    }

    /**
     * Getter for the server name
     * @return return the value in the server name text field
     */
    public JTextField getServerName() {
        return serverName;
    }

    /**
     * Getter for the server port
     * @return return the value in the server port text field
     */
    public JTextField getServerPort() {
        return serverPort;
    }
}
