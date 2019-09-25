package emse.ismin.minesweeper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoThread implements Runnable {
    private Socket socket;
    private DataInputStream inStream;
    private DataOutputStream outStream;
    private ServerGui serverGui;
    private int clientID;
    private String clientName;
    //
    private Thread process;

    /**
     * Constructor for EchoThread
     * @param clientSocket client socket
     */
    public EchoThread(Socket clientSocket, int clientID, ServerGui serverGui) {
        this.socket = clientSocket;
        this.serverGui = serverGui;
        this.clientID = clientID;
        process = new Thread(this);
        process.start();
    }

    @Override
    public void run() {
        inStream = null;
        try {
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
            while(process != null) {
                int cmd = inStream.readInt();
                if(cmd == ServerMessageTypes.CONNEXION.value()) {
                    sendConnectionMessage();
                }
                else if(cmd == ServerMessageTypes.DISCONNECTION.value()) {
                    serverGui.addMsg(clientName + " is disconnected !");
                    process = null;
                    inStream.close();
                    outStream.close();
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendConnectionMessage() {
        clientName = null;
        try {
            clientName = inStream.readUTF();
            outStream.writeInt(ServerMessageTypes.CONNEXION.value());
            outStream.writeUTF("Connected to the server: ");
            outStream.writeInt(clientID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverGui.addMsg("\n" + clientName + " is connected !\n");
    }

    /**
     * Getter for the client input stream
     * @return DataInputStream object
     */
    public DataInputStream getInStream() {
        return inStream;
    }

    /**
     * Getter for the client output stream
     * @return DataOutputStream object
     */
    public DataOutputStream getOutStream() {
        return outStream;
    }
}
