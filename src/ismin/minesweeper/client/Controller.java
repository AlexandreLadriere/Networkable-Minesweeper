package ismin.minesweeper.client;

import ismin.minesweeper.enums.FileNames;
import ismin.minesweeper.enums.Level;
import ismin.minesweeper.utils.CustomJOptionPane;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * This class implements the Controller for the client application
 */
public class Controller implements ActionListener {

    private Gui gui;

    /**
     * Creates a Controller for the given GUI
     * @param gui GUI you want to control
     */
    public Controller(Gui gui) {
        this.gui = gui;
    }

    /**
     * Manages events occurring on the GUI
     * @param e <code>ActionEvent</code>
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object cmd = e.getSource();
        if (cmd.equals(gui.getQuitButton()) || cmd.equals(gui.getmQuit())) {
            if(JOptionPane.showConfirmDialog(null, "Are you sure ?", "Exit Confimation", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION) {
                if(gui.getMinesweeper().getIsOnline()) {
                    gui.getMinesweeper().disconnectFromServer();
                }
                System.exit(0);
            }
        }
        else if(cmd.equals(gui.getGoOnlineButton()) && gui.getGoOnlineButton().getText().equals("Go online")) {
            gui.getGoOnlineButton().setText("Hide online panel");
            gui.getEastBorderLayout().setVisible(true);
            gui.getMinesweeper().pack();
        }
        else if(cmd.equals(gui.getGoOnlineButton()) && gui.getGoOnlineButton().getText().equals("Hide online panel")) {
            gui.getGoOnlineButton().setText("Go online");
            gui.getEastBorderLayout().setVisible(false);
            gui.getMinesweeper().pack();
        }
        else if(cmd.equals(gui.getConnexionButton()) && gui.getConnexionButton().getText().equals("Connexion")) {
            if(gui.getMinesweeper().connectToServer()) {
                gui.getConnexionButton().setText("Disconnect");
            }
        }
        else if(cmd.equals(gui.getConnexionButton()) && gui.getConnexionButton().getText().equals("Disconnect")) {
            gui.getConnexionButton().setText("Connexion");
            gui.getMinesweeper().disconnectFromServer();
            gui.getMinesweeper().setOnline(false);
            gui.newGame(Level.EASY);
        }
        else if(cmd.equals(gui.getChatMsg())) {
            String msg = gui.getChatMsg().getText();
            gui.getChatMsg().setText("");
            if(gui.getMinesweeper().getIsOnline()) {
                gui.getMinesweeper().sendChatMsg(msg);
            }
        }
        else if(cmd.equals(gui.getnewGameButton()) || cmd.equals(gui.getmNew())) {
            if(gui.getMinesweeper().getIsOnline()) {
                JOptionPane.showMessageDialog(null, "Impossible ! You are still connected to a server.\nPlease log out from this server if you want to use this functionality.", "Not authorized", JOptionPane.WARNING_MESSAGE);
            }
            else {
                gui.newGame();
            }
        }
        else if(cmd.equals(gui.getEasyRadio())) {
            if(gui.getMinesweeper().getIsOnline()) {
                JOptionPane.showMessageDialog(null, "Impossible ! You are still connected to a server.\nPlease log out from this server if you want to use this functionality.", "Not authorized", JOptionPane.WARNING_MESSAGE);
            }
            else {
                gui.newGame(Level.EASY);
            }
        }
        else if(cmd.equals(gui.getMediumRadio())) {
            if(gui.getMinesweeper().getIsOnline()) {
                JOptionPane.showMessageDialog(null, "Impossible ! You are still connected to a server.\nPlease log out from this server if you want to use this functionality.", "Not authorized", JOptionPane.WARNING_MESSAGE);
            }
            else {
                gui.newGame(Level.MEDIUM);
            }
        }
        else if(cmd.equals(gui.getHardRadio())) {
            if(gui.getMinesweeper().getIsOnline()) {
                JOptionPane.showMessageDialog(null, "Impossible ! You are still connected to a server.\nPlease log out from this server if you want to use this functionality.", "Not authorized", JOptionPane.WARNING_MESSAGE);
            }
            else {
                gui.newGame(Level.HARD);
            }
        }
        else if(cmd.equals(gui.getCustomRadio())) {
            if(gui.getMinesweeper().getIsOnline()) {
                JOptionPane.showMessageDialog(null, "Impossible ! You are still connected to a server.\nPlease log out from this server if you want to use this functionality.", "Not authorized", JOptionPane.WARNING_MESSAGE);
            }
            else {
                CustomJOptionPane customPanel = new CustomJOptionPane();
                int dimX = 0;
                int dimY = 0;
                int nbMines = 1;
                boolean cancel = false;
                while (dimX < 3 || dimY < 3 || dimX * dimY == 0 || nbMines >= dimX * dimY) {
                    if (JOptionPane.showConfirmDialog(null, customPanel, "Custom parameters", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        dimX = Integer.parseInt(customPanel.getDimXTextField().getText());
                        dimY = Integer.parseInt(customPanel.getDimYTextField().getText());
                        nbMines = Integer.parseInt(customPanel.getNbMinesTextField().getText());
                    }
                    else {
                        cancel = true;
                        break;
                    }
                }
                if(!cancel) {
                    Level.CUSTOM.dimX = dimX;
                    Level.CUSTOM.dimY = dimY;
                    Level.CUSTOM.nbMines = nbMines;
                    gui.newGame(Level.CUSTOM);
                }
            }
        }
        else if(cmd.equals(gui.getmInfo())) {
            JEditorPane ep = new JEditorPane("text/html", "<html><body> <b>Author:</b> <a href=\"https://www.linkedin.com/in/alexandre-ladriere/\">Alexandre Ladrière</a> <br><br> <b>Repository:</b> <a href=\"https://github.com/AlexandreLadriere/Networkable-Minesweeper\">AlexandreLadriere/Networkable-Minesweeper</a> <br><br> <b>Date:</b> September 2019 <br><br> <b>Language:</b> JAVA <br><br> <b>License:</b> MIT</body></html>");
            ep.addHyperlinkListener(e1 -> {
                if (e1.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                    try {
                        Desktop.getDesktop().browse(e1.getURL().toURI());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            ep.setEditable(false);
            JOptionPane.showMessageDialog(null, ep, "Infos", JOptionPane.PLAIN_MESSAGE);
        }
        else if(cmd.equals(gui.getmScores())) {
            JEditorPane ep = new JEditorPane("text/html", gui.getMinesweeper().getAllScoresToDisplay());
            ep.setEditable(false);
            JOptionPane.showMessageDialog(null, ep, "Best scores by level", JOptionPane.PLAIN_MESSAGE);
        }
        else if(cmd.equals(gui.getmStatistics())) {
            if(!gui.getMinesweeper().getAllLines(FileNames.STATS_FILENAME.toString()).isEmpty()) {
                List<String> allLines = gui.getMinesweeper().getAllLines(FileNames.STATS_FILENAME.toString());
                JEditorPane ep = new JEditorPane("text/html", "<html><body> "
                        + "<center><b>Games played: </b>" + allLines.get(0) + "<br>\n" +
                        "<b>Games won: </b>" + allLines.get(1) + "<br>\n" +
                        "<b>Games lost: </b>" + allLines.get(2) + "<br>\n" +
                        "<b>Win ratio: </b>" + allLines.get(3) + "</center><br>\n" +
                        "<br>\n" +
                        "<table>\n" +
                        "   <tr>\n" +
                        "       <th>Level</th>\n" +
                        "       <th>Played</th>\n" +
                        "       <th>Wins</th>\n" +
                        "       <th>Wins ratio</th>\n" +
                        "   </tr>\n" +
                        "   <tr>\n" +
                        "       <th>Easy</th>\n" +
                        "       <td>" + allLines.get(4) + "</td>\n" +
                        "       <td>" + allLines.get(5) + "</td>\n" +
                        "       <td>" + allLines.get(6) + "</td>\n" +
                        "   </tr>\n" +
                        "   <tr>\n" +
                        "       <th>Medium</th>\n" +
                        "       <td>" + allLines.get(7) + "</td>\n" +
                        "       <td>" + allLines.get(8) + "</td>\n" +
                        "       <td>" + allLines.get(9) + "</td>\n" +
                        "   </tr>\n" +
                        "    <tr>\n" +
                        "       <th>Hard</th>\n" +
                        "       <td>" + allLines.get(10) + "</td>\n" +
                        "       <td>" + allLines.get(11) + "</td>\n" +
                        "       <td>" + allLines.get(12) + "</td>\n" +
                        "   </tr>\n" +
                        "    <tr>\n" +
                        "       <th>Custom</th>\n" +
                        "       <td>" + allLines.get(13) + "</td>\n" +
                        "       <td>" + allLines.get(14) + "</td>\n" +
                        "       <td>" + allLines.get(15) + "</td>\n" +
                        "   </tr>\n" +
                        "</table>"
                        + "</body></html>");
                ep.setEditable(false);
                JOptionPane.showMessageDialog(null, ep, "Statistics", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "No statistics are available, you didn't play your first game !", "Statistics", JOptionPane.PLAIN_MESSAGE);
            }
        }
        else if(cmd.equals(gui.getmLicense())) {
            JOptionPane.showMessageDialog(null, "MIT License\n" +
                    "\n" +
                    "Copyright (c) 2019 Alexandre Ladrière\n" +
                    "\n" +
                    "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                    "of this software and associated documentation files (the \"Software\"), to deal\n" +
                    "in the Software without restriction, including without limitation the rights\n" +
                    "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                    "copies of the Software, and to permit persons to whom the Software is\n" +
                    "furnished to do so, subject to the following conditions:\n" +
                    "\n" +
                    "The above copyright notice and this permission notice shall be included in all\n" +
                    "copies or substantial portions of the Software.\n" +
                    "\n" +
                    "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                    "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                    "FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE\n" +
                    "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                    "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                    "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                    "SOFTWARE.", "License", JOptionPane.PLAIN_MESSAGE);
        }
    }
}

