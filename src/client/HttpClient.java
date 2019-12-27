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

        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        // TODO read response
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
