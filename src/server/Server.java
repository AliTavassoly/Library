package server;

import server.network.ClientHandler;
import shared.model.request.Request;
import shared.model.response.Response;
import shared.model.response.ResponseStatus;
import util.extra.Token;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private int port;

    private Library library;

    private ArrayList<ClientHandler> clients; // All of clients

    private Token tokenGenerator;

    public Server(int port) {
        this.port = port;
        this.tokenGenerator = new Token();

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
                ClientHandler clientHandler = new ClientHandler(tokenGenerator.generateToken(), this, socket);
                clients.add(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clientDisconnected() {
        // Remove from list
    }

    public void handleRequest(String token, Request request) {
        System.out.println(request);

        Boolean result;

        switch (request.getRequestType()) {
            case LOGIN:
                result = library.login((String) request.getData("username"), (String) request.getData("password"));
                sendLoginResponse(token, result, (String) request.getData("username"));
                break;
            case REGISTER:
                result = library.register((String) request.getData("username"), (String) request.getData("password"));
                sendRegisterResponse(token, result, (String) request.getData("username"));
                break;
        }
    }

    private ClientHandler getClientHandler(String token) {
        for(ClientHandler clientHandler: clients) {
            if (clientHandler.getToken().equals(token)) {
                return clientHandler;
            }
        }
        return null;
    }

    private void sendLoginResponse(String token, Boolean result, String username) {
        Response response;

        if (result) {
            response = new Response(ResponseStatus.OK);
            response.addData("username", username);
        } else {
            response = new Response(ResponseStatus.ERROR);
            response.setErrorMessage("Username or password is wrong!");
        }

        getClientHandler(token).sendResponse(response);
    }

    private void sendRegisterResponse(String token, Boolean result, String username) {
        Response response;

        if (result) {
            response = new Response(ResponseStatus.OK);
            response.addData("username", username);
        } else {
            response = new Response(ResponseStatus.ERROR);
            response.setErrorMessage("Username already exists!");
        }

        getClientHandler(token).sendResponse(response);
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
