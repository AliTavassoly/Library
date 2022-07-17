package server;

import server.network.ClientHandler;
import shared.request.Request;
import shared.response.Response;
import shared.response.ResponseStatus;
import shared.util.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final ArrayList<ClientHandler> clients; // All of clients
    private static int clientCount = 0;

    private ServerSocket serverSocket;
    private Library library;

    private final int port;
    private boolean running;

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;

            initializeLibrary();
            listenForNewConnection();
        } catch (IOException e) {
            e.printStackTrace(); // Failed to run the server
        }
    }

    @SuppressWarnings("unused")
    public void stop() {
        try {
            serverSocket.close();
            running = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeLibrary() {
        // Load library from database
        library = new Library();
    }

    private void listenForNewConnection() {
        while (running) {
            try {
                clientCount++;
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientCount, this, socket);
                clients.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unused")
    public void clientDisconnected(ClientHandler clientHandler) {
        // Remove client from clients
        clients.remove(clientHandler);
    }

    public void handleRequest(int clientId, Request request) {
        System.out.println(request);
        boolean result;
        switch (request.getRequestType()) {
            case LOGIN -> {
                result = library.login((String) request.getData("username"), (String) request.getData("password"));
                sendLoginResponse(clientId, result, (String) request.getData("username"));
            }
            case REGISTER -> {
                result = library.register((String) request.getData("username"), (String) request.getData("password"));
                sendRegisterResponse(clientId, result, (String) request.getData("username"));
            }
        }
    }

    private ClientHandler getClientHandler(int clientId) {
        for(ClientHandler clientHandler: clients) {
            if (clientHandler.getId() == clientId) {
                return clientHandler;
            }
        }
        return null;
    }

    private void findClientAndSendResponse(int clientId, Response response) {
        ClientHandler clientHandler = getClientHandler(clientId);
        if (clientHandler != null) {
            clientHandler.sendResponse(response);
        }
    }

    private void sendLoginResponse(int clientId, Boolean result, String username) {
        Response response;

        if (result) {
            response = new Response(ResponseStatus.OK);
            response.addData("username", username);
        } else {
            response = new Response(ResponseStatus.ERROR);
            String message = Config.getConfig().getProperty(String.class, "passwordWrongError");
            response.setErrorMessage(message);
        }

        findClientAndSendResponse(clientId, response);
    }

    private void sendRegisterResponse(int clientId, Boolean result, String username) {
        Response response;

        if (result) {
            response = new Response(ResponseStatus.OK);
            response.addData("username", username);
        } else {
            response = new Response(ResponseStatus.ERROR);
            String message = Config.getConfig().getProperty(String.class, "usernameTakenError");
            response.setErrorMessage(message);
        }

        findClientAndSendResponse(clientId, response);
    }
}
