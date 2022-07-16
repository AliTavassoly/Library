package server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(1234); // config
        server.start();
    }
}
