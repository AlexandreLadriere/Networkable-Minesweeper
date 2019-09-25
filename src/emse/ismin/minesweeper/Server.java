package emse.ismin.minesweeper;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {

    private static final int SERVER_PORT = 10000;
    ServerGui serverGui;

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
            ServerSocket serverSock = new ServerSocket(SERVER_PORT);
            Socket sock = serverSock.accept();
            DataInputStream inStream = new DataInputStream(sock.getInputStream());
            DataOutputStream outStream = new DataOutputStream(sock.getOutputStream());

            String clientName = inStream.readUTF();
            serverGui.addMsg("\n" + clientName + " is connected !\n");

            outStream.close();
            inStream.close();
            sock.close();
            serverSock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
