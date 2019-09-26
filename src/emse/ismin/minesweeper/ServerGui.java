package emse.ismin.minesweeper;

import javax.swing.*;
import java.awt.*;

public class ServerGui extends JPanel {

    private Server server;
    private JButton startButton;
    private JTextArea msgArea;
    private JRadioButtonMenuItem easyRadio;
    private JRadioButtonMenuItem mediumRadio;
    private JRadioButtonMenuItem hardRadio;

    public ServerGui(Server server) {
        this.server = server;

        this.setPreferredSize(new Dimension(300, 400));
        this.setLayout(new BorderLayout());
        setBackground(new Color(189,195,199));

        // NORTH panel of the main BorderLayout
        JLabel titleLabel = new JLabel("Minesweeper server");
            titleLabel.setForeground(new Color(44,62,80));
            titleLabel.setFont(new Font("Nunito", Font.BOLD, 18));
        JPanel topPanel = new JPanel();
            topPanel.setLayout(new FlowLayout());
            topPanel.add(titleLabel);
            topPanel.setBackground(new Color(189,195,199));
        this.add(topPanel, BorderLayout.NORTH);

        // CENTER panel of the main BorderLayout
        msgArea = new JTextArea();
        msgArea.setEditable(false);
        msgArea.setFont(new Font("Nunito", Font.PLAIN, 14));
        JScrollPane sp = new JScrollPane(msgArea);
        this.add(sp, BorderLayout.CENTER);

        // SOUTH panel of the main BorderLayout
        startButton = new JButton("Start game");
        startButton.setForeground(new Color(44,62,80));
        startButton.setFont(new Font("Nunito", Font.BOLD, 18));
        startButton.addActionListener(new ServerController(this));

        JPanel southPanel = new JPanel();
            southPanel.setLayout(new BorderLayout());
        JPanel northPanelBis = new JPanel();
            ButtonGroup group = new ButtonGroup();
            northPanelBis.setLayout(new FlowLayout());
            northPanelBis.setBackground(new Color(189,195,199));
            easyRadio = new JRadioButtonMenuItem("Easy");
            easyRadio.setSelected(true);
            easyRadio.setForeground(new Color(44,62,80));
            easyRadio.setFont(new Font("Nunito", Font.BOLD, 14));
            group.add(easyRadio);
            mediumRadio = new JRadioButtonMenuItem("Medium");
            mediumRadio.setForeground(new Color(44,62,80));
            mediumRadio.setFont(new Font("Nunito", Font.BOLD, 14));
            group.add(mediumRadio);
            hardRadio = new JRadioButtonMenuItem("Hard");
            hardRadio.setForeground(new Color(44,62,80));
            hardRadio.setFont(new Font("Nunito", Font.BOLD, 14));
            group.add(hardRadio);
        northPanelBis.add(easyRadio); northPanelBis.add(mediumRadio); northPanelBis.add(hardRadio);
        JPanel southPanelBis = new JPanel();
            southPanelBis.setLayout(new FlowLayout());
            southPanelBis.setBackground(new Color(189,195,199));
            southPanelBis.add(startButton);
        southPanel.add(northPanelBis, BorderLayout.NORTH);
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
}
