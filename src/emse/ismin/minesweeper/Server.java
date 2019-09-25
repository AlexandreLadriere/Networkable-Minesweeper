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
        serverGui.addMsg("Waiting for clients...\n\n");
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
            clientID++;
            new Thread(this).start();
            clientThread = new EchoThread(sock, clientID, serverGui);
            clientThread.start();
            clientThreadList.add(clientThread);
        } catch (IOException e) {
            serverGui.addMsg("\n\nError while listening on socket - shutting down server");
            closeServer();
            e.printStackTrace();
        }
    }
}
