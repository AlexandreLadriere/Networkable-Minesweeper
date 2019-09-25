package emse.ismin.minesweeper;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends JFrame implements Runnable {

    private static final int SERVER_PORT = 10000;
    private ServerGui serverGui;
    private int clientID = 0;
    private ServerSocket serverSock;
    private Socket sock;
    private List<EchoThread> clientThreadList = new ArrayList<>();

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
        clientID = 0;
        try {
            serverSock = new ServerSocket(SERVER_PORT);
            sock = null;
            new Thread(this).start();
        } catch (IOException e) {
            serverGui.addMsg("\n\nError while opening server socket - shutting down the server");
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
            clientThread = new EchoThread(sock, clientID, serverGui);
            //clientThread.start();
            clientThreadList.add(clientThread);
            clientID++;
        } catch (IOException e) {
            serverGui.addMsg("\n\nError while listening on socket - shutting down server");
            closeServer();
            e.printStackTrace();
        }
    }

    /**
     * Broadcast a <code>String</code> message
     * @param msg <code>String</code> message you want to broadcast
     */
    public void broadcast(String msg) {
        for (EchoThread echoThread : clientThreadList) {
            try {
                echoThread.getOutStream().writeInt(ServerMessageTypes.MSG.value());
                echoThread.getOutStream().writeUTF(msg);
            } catch (IOException e) {
                serverGui.addMsg("Impossible to broadcast string message \n");
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
                echoThread.getOutStream().writeInt(ServerMessageTypes.INT.value());
                echoThread.getOutStream().writeInt(msg);
            } catch (IOException e) {
                serverGui.addMsg("Impossible to broadcast string message \n");
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
                echoThread.getOutStream().writeInt(ServerMessageTypes.BOOL.value());
                echoThread.getOutStream().writeBoolean(msg);
            } catch (IOException e) {
                serverGui.addMsg("Impossible to broadcast string message \n");
                e.printStackTrace();
            }
        }
    }
}
