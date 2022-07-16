package client;

import client.network.ServerController;
import shared.model.response.Response;
import shared.model.response.ResponseStatus;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Scanner scanner = new Scanner(System.in);

    private ServerController serverController;
    private int port;
    InetAddress inetAddress;

    public Client(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    private void loginCLI() {
        System.out.println("Login:");

        while (true) {
            int command = scanner.nextInt();

            switch (command) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
            }
        }
    }

    private void login(){
        String username = scanner.nextLine(); // username
        String password = scanner.nextLine(); // password

        Response response = serverController.sendLoginRequest(username, password);

        if (response.getStatus() == ResponseStatus.OK) {
            mainMenuCLI();
        } else {
            System.err.println(response.getErrorMessage());
        }
    }

    private void register() {
        String username = scanner.nextLine(); // username
        String password = scanner.nextLine(); // password

        Response response = serverController.sendRegisterRequest(username, password);

        if (response.getStatus() == ResponseStatus.OK) {
            mainMenuCLI();
        } else {
            System.err.println(response.getErrorMessage());
        }
    }

    private void mainMenuCLI() {
        System.out.println("Main menu:");

    }

    public void start() {
        try {
            serverController = new ServerController(InetAddress.getLocalHost(), port);
            serverController.connectToServer();
            loginCLI();
        } catch (UnknownHostException e) {
            e.printStackTrace(); // Handle connection refuse
        }
    }
}
