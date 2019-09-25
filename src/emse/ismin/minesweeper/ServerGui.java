package emse.ismin.minesweeper;

import javax.swing.*;
import java.awt.*;

public class ServerGui extends JPanel {

    private Server server;
    private JButton startButton;
    private JTextArea msgArea;

    public ServerGui(Server server) {
        this.server = server;

        this.setPreferredSize(new Dimension(200, 300));
        this.setLayout(new BorderLayout());
        setBackground(new Color(189,195,199));

        // top panel of the BorderLayout
        JLabel titleLabel = new JLabel("Minesweeper server");
        titleLabel.setForeground(new Color(44,62,80));
        titleLabel.setFont(new Font("Nunito", Font.BOLD, 18));

        // bottom panel of the main BorderLayout
        startButton = new JButton("Start game");
        startButton.setForeground(new Color(44,62,80));
        startButton.setFont(new Font("Nunito", Font.BOLD, 18));
        startButton.addActionListener(new ServerController(this));

        // center of the BorderLayout
        msgArea = new JTextArea();
        msgArea.setEditable(false);

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(titleLabel);
        topPanel.setBackground(new Color(189,195,199));
        this.add(topPanel, BorderLayout.NORTH);

        // Center panel
        JScrollPane sp = new JScrollPane(msgArea);
        this.add(sp, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(startButton);
        bottomPanel.setBackground(new Color(189,195,199));
        this.add(bottomPanel, BorderLayout.SOUTH);
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
