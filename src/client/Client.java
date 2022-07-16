package client;

import client.network.ServerController;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {
    private ServerController serverController;
    private int port;
    InetAddress inetAddress;

    public Client(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    private void loginCLI() {
        System.out.println("Login:");


    }

    private void mainMenuCLI() {
        System.out.println("Main menu:");


    }

    public void start() {
        try {
            serverController = new ServerController(InetAddress.getLocalHost(), port);
            serverController.connectToServer(); // Handle connection refuse
            loginCLI();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
