package client.network;

import org.codehaus.jackson.map.ObjectMapper;
import shared.request.Request;
import shared.request.RequestType;
import shared.response.Response;
import shared.util.Jackson;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ServerController {
    private PrintStream printStream;
    private Scanner scanner;

    private final int port;

    private final ObjectMapper objectMapper;

    public ServerController(int port) {
        this.port = port;
        objectMapper = Jackson.getNetworkObjectMapper();
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), port);
            printStream = new PrintStream(socket.getOutputStream());
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(Request request) {
        try {
            String requestString = objectMapper.writeValueAsString(request);
            printStream.println(requestString);
            printStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response sendLoginRequest(String username, String password) {
        Request request = new Request(RequestType.LOGIN);
        request.addData("username", username);
        request.addData("password", password);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response sendRegisterRequest(String username, String password) {
        Request request = new Request(RequestType.REGISTER);
        request.addData("username", username);
        request.addData("password", password);
        sendRequest(request);

        Response response = null;
        try {
            response = objectMapper.readValue(scanner.nextLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
