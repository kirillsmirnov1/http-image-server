package client;

import httpUtil.HttpRequestParser;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;

public class HttpClient {
    private final int port;

    private Socket socket;
    private OutputStream outputStream;
    private BufferedReader bufferedReader;

    private boolean connected = false;

    HttpClient(int port){
        this.port = port;
    }

    public void connectToServer(){
        System.out.println("Connecting server with port: " + port);
        try{
            socket = new Socket(InetAddress.getByName(null), port);

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            outputStream = socket.getOutputStream();

            connected = true;

        } catch (IOException e) {
            System.out.println("Couldn't connect to a server");
            connected = false;
        }
    }

    public void sendPostRequest(String filename){

        File file = new File(filename);

        String header = HttpRequestParser.preparePostRequestHeader(filename, file.length());

        System.out.println("\nSending\n" + header);

        try {
            outputStream.write(header.getBytes());
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            System.out.println("Couldn't send POST request to a server");
            connected = false;
            return;
        }

        // TODO read response
    }

    public void sendGetRequest(String filename){

        String header = HttpRequestParser.prepareGetRequest(filename);

        System.out.println("\nSending\n" + header);

        try {
            outputStream.write(header.getBytes());
        } catch (IOException e) {
            System.out.println("Couldn't send GET request to a server");
            connected = false;
            return;
        }

        // TODO read response
    }

    public void stop() {
        System.out.println("Destroying connection to server");

        connected = false;

        try {
            outputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }
}
