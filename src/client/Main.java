package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 1234); // config
            client.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
