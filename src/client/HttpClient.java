package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class HttpClient {
    private final int port;

    private Socket socket;

    HttpClient(int port){
        this.port = port;
    }

    public void connectToServer(){
        System.out.println("Connecting server with port: " + port);
        try{
            socket = new Socket(InetAddress.getByName(null), port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPostRequest(String filename){
        System.out.println("Should send post request for " + filename);
        // TODO
    }

    public void sendGetRequest(String filename){
        System.out.println("Should send get request for " + filename);
        // TODO
    }

    public void stop() {
        System.out.println("Should destroy connection to server");
        // TODO
    }
}
