package emse.ismin.minesweeper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoThread implements Runnable {
    private Socket socket;
    private DataInputStream inStream;
    private DataOutputStream outStream;
    private Server server;
    private String clientName;
    private Thread process;

    /**
     * Constructor for EchoThread
     * @param clientSocket client socket
     */
    public EchoThread(Socket clientSocket, Server server) {
        this.socket = clientSocket;
        this.server = server;
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
                else if(cmd == ServerMessageTypes.CASE_CLICKED.value()) {
                    int x = inStream.readInt();
                    int y = inStream.readInt();
                    if(server.getTabNames()[x][y].equals("none")) {
                        server.getServerGui().addMsg(clientName + " has clicked on (" + x + ", " + y + ")\n");
                        server.getTabNames()[x][y] = clientName;
                    }
                    else {
                        outStream.writeInt(ServerMessageTypes.MSG.value());
                        outStream.writeUTF("Case already clicked by " + server.getTabNames()[x][y] + "\n");
                    }
                }
                else if(cmd == ServerMessageTypes.DISCONNECTION.value()) {
                    server.getServerGui().addMsg(clientName + " is disconnected !\n");
                    process = null;
                    inStream.close();
                    outStream.close();
                    socket.close();
                    server.getClientThreadList().remove(this);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.getServerGui().addMsg(clientName + " is connected !\n");
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
