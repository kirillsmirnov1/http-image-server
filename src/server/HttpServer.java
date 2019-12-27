package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class HttpServer implements Runnable{

    private final int port;
    private boolean acceptingConnections = true;

    private ServerSocket serverSocket;

    HttpServer(int port){
        this.port = port;
    }

    @Override
    public void run(){

        try{
            serverSocket = new ServerSocket(port);

            System.out.println("Server started");

            while (acceptingConnections){

                try{
                    Socket clientSocket = serverSocket.accept();

                    ServerToClientConnection connection = new ServerToClientConnection(clientSocket);
                    Thread clientThread = new Thread(connection);
                    clientThread.start();

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
