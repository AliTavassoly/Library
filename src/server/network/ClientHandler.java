package server.network;

import server.Server;
import shared.request.Request;
import org.codehaus.jackson.map.ObjectMapper;
import shared.response.Response;
import shared.util.Jackson;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler {
    private PrintStream printStream;
    private Scanner scanner;

    private ObjectMapper objectMapper;

    private Server server;

    private String token;

    public ClientHandler(String token, Server server, Socket socket) {
        this.token = token;
        this.server = server;

        try {
            printStream = new PrintStream(socket.getOutputStream());
            scanner = new Scanner(socket.getInputStream());

            objectMapper = Jackson.getNetworkObjectMapper();

            makeListenerThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        return token;
    }

    private void makeListenerThread() {
        Thread thread = new Thread(() -> {
            while (true) {
                String requestString = scanner.nextLine();
                try {
                    Request request = objectMapper.readValue(requestString, Request.class);
                    handleRequest(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void handleRequest(Request request) {
        server.handleRequest(token, request);
    }

    public void sendResponse(Response response) {
        try {
            String responseString = objectMapper.writeValueAsString(response);
            printStream.println(responseString);
            printStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
