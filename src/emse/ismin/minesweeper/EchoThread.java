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
                    sendConnexionMessage();
                }
                else if(cmd == ServerMessageTypes.CASE_CLICKED.value()) {
                    int x = inStream.readInt();
                    int y = inStream.readInt();
                    if(server.getTabNames()[x][y].equals("none")) {
                        server.getServerGui().addMsg(clientName + " has clicked on (" + x + ", " + y + ")\n");
                        server.getTabNames()[x][y] = clientName;
                        server.setNbRevealed(server.getNbRevealed() + 1);
                        server.broadcast(ServerMessageTypes.CASE_CLICKED.value());
                        server.broadcast(x);
                        server.broadcast(y);
                        int nearbyCount = server.getServerField().countNearbyMines(x, y);
                        server.broadcast(nearbyCount);
                        if(nearbyCount == -1) {
                            outStream.writeInt(ServerMessageTypes.MINE_CLICKED.value());
                        }
                        server.isWin();
                    }
                    else {
                        outStream.writeInt(ServerMessageTypes.MSG.value());
                        outStream.writeUTF("Case already clicked by " + server.getTabNames()[x][y] + "\n");
                    }
                }
                else if(cmd == ServerMessageTypes.DISCONNECTION.value()) {
                    server.getServerGui().addMsg(clientName + " is disconnected !\n");
                    disconnectClient();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a connexion message
     */
    private void sendConnexionMessage() {
        clientName = null;
        try {
            clientName = inStream.readUTF();
            if(!needToChangeName(clientName)) {
                outStream.writeInt(ServerMessageTypes.CONNEXION.value());
                outStream.writeUTF("Connected to the server: ");
                server.getServerGui().addMsg(clientName + " is connected !\n");
                if(server.getGameStarted()) {
                    outStream.writeInt(ServerMessageTypes.ALREADY_STARTED.value());
                }
            }
            else {
                outStream.writeInt(ServerMessageTypes.CHANGE_NAME.value());
                disconnectClient();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the client name already exists or not
     * @param clientName name that you want to check
     * @return a boolean that indicates if the name already exists or not
     */
    private boolean needToChangeName(String clientName) {
        boolean exist = false;
        for (EchoThread echoThread : server.getClientThreadList()) {
            if(echoThread.clientName.equals(clientName) && echoThread != this) {
                exist = true;
            }
        }
        return exist;
    }

    /**
     * Close the connection between the server and the client, and remove the client form the server's client list
     */
    private void disconnectClient() {
        process = null;
        try {
            inStream.close();
            outStream.close();
            socket.close();
            server.getClientThreadList().remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Getter for the client name
     * @return client name
     */
    public String getClientName() {
        return clientName;
    }
}
