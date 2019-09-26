package emse.ismin.minesweeper;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class Server extends JFrame implements Runnable {

    private static final int SERVER_PORT = 10000;
    private ServerGui serverGui;
    private ServerSocket serverSock;
    private Socket sock;
    private HashSet<EchoThread> clientThreadList = new HashSet<>();
    private Field serverField;

    /**
     * Server Constructor
     */
    Server() {
        super("Server");
        //iOS look
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WikiTeX");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        serverGui = new ServerGui(this);

        setContentPane(serverGui);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        serverGui.addMsg("Server started\n");
        startServer();
    }

    /**
     * Server main
     * @param args
     */
    public static void main(String[] args) {
        new Server();
    }

    void startServer() {
        serverGui.addMsg("Waiting for clients...\n");
        try {
            serverSock = new ServerSocket(SERVER_PORT);
            sock = null;
            new Thread(this).start();
        } catch (IOException e) {
            serverGui.addMsg("\n\nERROR: Error while opening server socket - shutting down the server");
            closeServer();
            e.printStackTrace();
        }
    }

    /**
     * Closes the server socket
     */
    void closeServer() {
        try {
            sock.close();
            serverSock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        EchoThread clientThread;
        try {
            sock = serverSock.accept();
            new Thread(this).start();
            clientThread = new EchoThread(sock, this);
            clientThreadList.add(clientThread);
        } catch (IOException e) {
            serverGui.addMsg("\n\nERROR: Error while listening on socket - shutting down server");
            closeServer();
            e.printStackTrace();
        }
    }

    /**
     * Starts a new game for the server (and the client). The server Field is initialized here
     */
    public void startGame() {
        broadcast(ServerMessageTypes.MSG.value());
        broadcast("New game started ! \n");
        broadcast(ServerMessageTypes.START_GAME.value());
        if(serverGui.getEasyRadio().isSelected()) {
            this.serverField = new Field(Level.EASY);
            broadcast(Level.EASY.toString());
            broadcast(Level.EASY.dimX);
            broadcast(Level.EASY.dimY);
        }
        else if(serverGui.getMediumRadio().isSelected()) {
            this.serverField = new Field(Level.MEDIUM);
            broadcast(Level.MEDIUM.toString());
            broadcast(Level.MEDIUM.dimX);
            broadcast(Level.MEDIUM.dimY);
        }
        else if(serverGui.getHardRadio().isSelected()) {
            this.serverField = new Field(Level.HARD);
            broadcast(Level.HARD.toString());
            broadcast(Level.HARD.dimX);
            broadcast(Level.HARD.dimY);
        }
    }

    /**
     * Broadcast a <code>String</code> message
     * @param msg <code>String</code> message you want to broadcast
     */
    public void broadcast(String msg) {
        for (EchoThread echoThread : clientThreadList) {
            try {
                echoThread.getOutStream().writeUTF(msg);
            } catch (IOException e) {
                serverGui.addMsg("ERROR: Impossible to broadcast string message \n");
                e.printStackTrace();
            }
        }
    }

    /**
     * Broadcast a <code>int</code> message
     * @param msg <code>int</code> message you want to broadcast
     */
    public void broadcast(int msg) {
        for (EchoThread echoThread : clientThreadList) {
            try {
                echoThread.getOutStream().writeInt(msg);
            } catch (IOException e) {
                serverGui.addMsg("ERROR: Impossible to broadcast string message \n");
                e.printStackTrace();
            }
        }
    }

    /**
     * Broadcast a <code>boolean</code> message
     * @param msg <code>boolean</code> message you want to broadcast
     */
    public void broadcast(boolean msg) {
        for (EchoThread echoThread : clientThreadList) {
            try {
                echoThread.getOutStream().writeBoolean(msg);
            } catch (IOException e) {
                serverGui.addMsg("ERROR: Impossible to broadcast string message \n");
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter for the server GUI
     * @return the server GUI
     */
    public ServerGui getServerGui() {
        return serverGui;
    }

    /**
     * Getter for the server client-thread HashSet
     * @return the server client-thread HashSet
     */
    public HashSet<EchoThread> getClientThreadList() {
        return clientThreadList;
    }

    /**
     * Getter for the server Field
     * @return the server field <code>serverField</code>
     */
    public Field getServerField() {
        return serverField;
    }
}
