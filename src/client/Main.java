package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        Client client = null; // config
        try {
            client = new Client(InetAddress.getLocalHost(), 1234);
            client.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
