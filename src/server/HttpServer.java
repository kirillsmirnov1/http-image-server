package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

public class HttpServer implements Runnable{

    private final String address;
    private final int port;
    private boolean acceptingConnections = true;

    private ServerSocket serverSocket;

    HttpServer(String address, int port){
        this.address = address;
        this.port = port;
    }

    @Override
    public void run(){
        // TODO start a thread which checks input connections

        try{
            serverSocket = new ServerSocket(port);

            System.out.println("Server started");

            while (acceptingConnections){

                try(Socket clientSocket = serverSocket.accept();
                    InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                    BufferedReader reader = new BufferedReader(isr)){                               // TODO add output stream

                    System.out.println("\nClient sent: \n");

                    String line = reader.readLine();
                    while (!line.isEmpty()) {
                        System.out.println(line);
                        line = reader.readLine();
                    }

                    String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + new Date();

                    System.out.println("\nWe are sending: \n" + httpResponse);

                    clientSocket.getOutputStream().write(httpResponse.getBytes());

                } catch (SocketException e){
                    System.out.println("Closed server socket");
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stop(){
        acceptingConnections = false;

        try {
            serverSocket.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        System.out.println("Server stopping");

        // TODO handle waiting for end of client connections
    }
}
