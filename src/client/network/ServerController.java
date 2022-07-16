package client.network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerController {
    private Socket socket;
    private PrintStream printStream;
    private Scanner scanner;

    private InetAddress inetAddress;
    private int port;

    public ServerController(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public void connectToServer() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), port);
            printStream = new PrintStream(socket.getOutputStream());
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest() {

    }
}
