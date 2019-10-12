package ismin.minesweeper.server;

import ismin.minesweeper.enums.Level;
import ismin.minesweeper.utils.CustomJOptionPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Implements the controller for the server
 */
public class ServerController implements ActionListener {

    private ServerGui serverGui;

    public ServerController(ServerGui serverGui) {
        this.serverGui = serverGui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object cmd = e.getSource();
        if(cmd.equals(serverGui.getStartButton()) && serverGui.getStartButton().getText().equals("Start Game")) {
            serverGui.addMsg("Game started\n");
            serverGui.getServer().startGame();
            serverGui.getStartButton().setText("Stop Game");
        }
        else if(cmd.equals(serverGui.getStartButton()) && serverGui.getStartButton().getText().equals("Stop Game")) {
            serverGui.addMsg("Game stopped\n");
            serverGui.getServer().stopGame();
            serverGui.getStartButton().setText("Start Game");
        }
        else if(cmd.equals(serverGui.getStartServerButton()) && serverGui.getStartServerButton().getText().equals("Start Server")) {
            serverGui.getStartServerButton().setText("Stop Server");
            serverGui.getStartButton().setEnabled(true);
            int port = Integer.parseInt(serverGui.getServerPort().getText());
            serverGui.getServer().startServer(port);
        }
        else if(cmd.equals(serverGui.getStartServerButton()) && serverGui.getStartServerButton().getText().equals("Stop Server")) {
            serverGui.getStartServerButton().setText("Start Server");
            serverGui.getStartButton().setEnabled(false);
            serverGui.getServer().closeServer();
        }
        else if(cmd.equals(serverGui.getLevelComboBox()) && serverGui.getLevelComboBox().getSelectedItem() == Level.CUSTOM) {
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
            }
        }
    }
}
