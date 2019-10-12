package ismin.minesweeper.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Implements a specific JOptionPane for custom level parameters
 */
public class CustomJOptionPane extends JPanel {
    private static final Color FOREGROUND_COLOR = new Color(44,62,80);
    private static final Color BACKGROUND_COLOR = new Color(189,195,199);
    private static final Font BUTTON_FONT = new Font("Nunito", Font.BOLD, 18);

    private JPanel mainPanel;
    private JPanel dimXPanel;
    private JPanel dimYPanel;
    private JPanel nbMinesPanel;
    private JTextField dimXTextField;
    private JTextField dimYTextField;
    private JTextField nbMinesTextField;

    public CustomJOptionPane() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        dimXPanel = new JPanel();
            dimXPanel.setLayout(new BorderLayout());
            // dimX Label
            JLabel dimXLabel = new JLabel("Number of rows: ");
            // dimX text field
            dimXTextField = new JTextField("0", 5);
        dimXPanel.add(dimXLabel, BorderLayout.WEST);
        dimXPanel.add(dimXTextField, BorderLayout.EAST);

        dimYPanel = new JPanel();
            dimYPanel.setLayout(new BorderLayout());
            // dimY Label
            JLabel dimYLabel = new JLabel("Number of columns: ");
            // dimY text field
            dimYTextField = new JTextField("0", 5);
        dimYPanel.add(dimYLabel, BorderLayout.WEST);
        dimYPanel.add(dimYTextField, BorderLayout.EAST);

        nbMinesPanel = new JPanel();
            nbMinesPanel.setLayout(new BorderLayout());
            // nbMines Label
            JLabel nbMinesLabel = new JLabel("Number of mines: ");
            // nbMines text field
            nbMinesTextField = new JTextField("0", 5);
        nbMinesPanel.add(nbMinesLabel, BorderLayout.WEST);
        nbMinesPanel.add(nbMinesTextField, BorderLayout.EAST);

        mainPanel.add(dimXPanel, BorderLayout.NORTH);
        mainPanel.add(dimYPanel, BorderLayout.CENTER);
        mainPanel.add(nbMinesPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    /**
     * Getter for the text field corresponding to the number of columns
     * @return <code>dimXTextField</code>
     */
    public JTextField getDimXTextField() {
        return dimXTextField;
    }

    /**
     * Getter for the text field corresponding to the number of mines
     * @return <code>nbMinesTextField</code>
     */
    public JTextField getNbMinesTextField() {
        return nbMinesTextField;
    }

    /**
     * Getter for the text field corresponding to the number of rows
     * @return <code>dimYTextField</code>
     */
    public JTextField getDimYTextField() {
        return dimYTextField;
    }
}
