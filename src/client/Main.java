package client;

import shared.util.Config;

public class Main {
    public static void main(String[] args) {
        Integer port = Config.getConfig().getProperty(Integer.class, "serverPort");
        Client client = new Client(port);
        client.start();
    }
}
