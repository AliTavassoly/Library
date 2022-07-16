package client;

import constants.Constants;
import shared.util.Config;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try {
            Integer port = Config.getConfig().getProperty(Integer.class, "serverPort");
            Client client = new Client(InetAddress.getLocalHost(), port);
            client.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
