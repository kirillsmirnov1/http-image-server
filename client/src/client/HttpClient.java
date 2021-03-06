package client;

import httpUtil.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;

import static httpUtil.HttpMethod.GET;
import static httpUtil.HttpMethod.POST;

public class HttpClient {
    private final int port;

    private Socket socket;
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;

    private boolean connected = false;

    HttpClient(int port){
        this.port = port;
    }

    public void connectToServer(){
        System.out.println("Connecting server with port: " + port);
        try{
            socket = new Socket(InetAddress.getByName(null), port);

            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
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

        String header = new HttpRequestHeader(POST, filename, (int)file.length()).toString();

        System.out.println("\nSending\n" + header);

        try {
            outputStream.write(header.getBytes());
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            System.out.println("Couldn't send POST request to a server");
            connected = false;
            return;
        }

        readResponse(POST, filename); // FIXME it would be nice to pass an HttpRequestHeader there
    }

    private void readResponse(HttpMethod method, String filename) {

        HttpResponseHeader responseHeader = HttpResponseParser.parseResponse(bufferedReader);

        if(responseHeader == null){
            connected = false;
            return;
        }

        System.out.println("Server response\n" + responseHeader.getHeaderContents());

        // FIXME rn it will accept file, even if it wasn't requested
        // TODO What should client do in that case?
        switch (responseHeader.getCode()){
            case OK_200:

                try {
                    switch (method){
                        case GET:
                            byte[] buffer = new byte[responseHeader.getContentLength()];

                            for(int i = 0; i < buffer.length; ++i){
                                buffer[i] = (byte)inputStream.read();
                            }

                            File file = new File(filename);
                            new FileOutputStream(file).write(buffer);
                            System.out.println("File " + filename + " saved");
                            break;
                        case POST:
                            String line;
                            do{
                                line = bufferedReader.readLine();
                                System.out.println(line);
                            } while (!line.isEmpty());
                            break;
                    }
                } catch (IOException e) {
                    System.out.println("Couldn't read server response");
                    connected = false;
                }

                break;
            case NOT_FOUND_404:
                System.out.println("Server couldn't find requested file");
                break;
            case NOT_ALLOWED_405:
                System.out.println("Server can't handle request");
                break;
            case SERVER_ERROR_500:
                System.out.println("Server failed at handling request");
                break;
            default:
                System.out.println("Code unknown");
        }

    }

    public void sendGetRequest(String filename){

        String header = new HttpRequestHeader(GET, filename, -1).toString();

        System.out.println("\nSending\n" + header);

        try {
            outputStream.write(header.getBytes());
        } catch (IOException e) {
            System.out.println("Couldn't send GET request to a server");
            connected = false;
            return;
        }

        readResponse(HttpMethod.GET, filename);
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
