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
    }
}
