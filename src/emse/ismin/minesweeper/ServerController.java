package emse.ismin.minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerController implements ActionListener {

    private ServerGui serverGui;

    public ServerController(ServerGui serverGui) {
        this.serverGui = serverGui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object cmd = e.getSource();
        if(cmd.equals(serverGui.getStartButton())) {
            serverGui.getStartButton().setText("Game started...");
            serverGui.getStartButton().setEnabled(false);
            serverGui.getServer().startGame();
        }
    }
}
