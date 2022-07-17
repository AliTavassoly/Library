package client;

import client.network.ServerController;
import shared.response.Response;
import shared.response.ResponseStatus;
import shared.util.Config;

import java.util.Scanner;

public class Client {
    private final Scanner scanner = new Scanner(System.in);

    private ServerController serverController;
    private final int port;

    public Client(int port) {
        this.port = port;
    }

    public void start() {
        serverController = new ServerController(port);
        serverController.connectToServer();
        loginCLI();
    }

    private void loginCLI() {
        System.out.println("Login Page:");

        while (true) {
            int command = scanner.nextInt();
            scanner.nextLine();

            Integer exitCode = Config.getConfig().getProperty(Integer.class, "exitCode");
            Integer loginCode = Config.getConfig().getProperty(Integer.class, "loginCode");
            Integer registerCode = Config.getConfig().getProperty(Integer.class, "registerCode");

            if (command == exitCode) {
                System.exit(0);
            } else if (command == loginCode) {
                login();
            } else if (command == registerCode) {
                register();
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
        System.out.println("Main Menu Page:");
    }
}
