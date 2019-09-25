package emse.ismin.minesweeper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoThread extends Thread {
    private Socket socket;
    private ServerGui serverGui;

    /**
     * Constructor for EchoThread
     * @param clientSocket client socket
     */
    public EchoThread(Socket clientSocket, ServerGui serverGui) {
        this.socket = clientSocket;
        this.serverGui = serverGui;
    }

    public void start() {
        DataInputStream inStream = null;
        try {
            inStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            String clientName = inStream.readUTF();
            serverGui.addMsg("\n" + clientName + " is connected !\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
