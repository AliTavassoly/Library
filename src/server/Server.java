package server;

import server.network.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private int port;

    private Library library;

    private ArrayList<ClientHandler> clients;

    public Server(int port) {
        this.port = port;

        clients = new ArrayList<>();
    }

    private void initializeLibrary() {
        // Load library from database
        library = new Library();
    }

    private void listenForNewConnection() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clientDisconnected() {

    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);

            initializeLibrary();

            listenForNewConnection();
        } catch (IOException e) {
            e.printStackTrace(); // Failed to run the server
        }
    }
}
