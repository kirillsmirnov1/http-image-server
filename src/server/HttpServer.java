package server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

import static server.SocketStatus.PROPERTY_SOCKET_OPEN;

public class HttpServer implements Runnable, PropertyChangeListener {

    private final int port;
    private boolean acceptingConnections = true;

    private ServerSocket serverSocket;

    Queue<ServerToClientConnection> connections;

    HttpServer(int port){
        this.port = port;
        connections = new LinkedList<>();
    }

    @Override
    public void run(){

        try{
            serverSocket = new ServerSocket(port);

            System.out.println("Server started");

            while (acceptingConnections){

                try{
                    Socket clientSocket = serverSocket.accept();

                    System.out.println("Client connected");

                    ServerToClientConnection connection = new ServerToClientConnection(clientSocket);
                    Thread clientThread = new Thread(connection);
                    clientThread.start();

                    connection.getSocketStatus().addPropertyChangeListener("socketOpen", this);

                    connections.add(connection);

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

        while(connections.peek() != null){
            connections.poll().timeToCloseConnection();
        }

        try {
            serverSocket.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        System.out.println("Server stopping");

        // TODO handle waiting for end of client connections
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(PROPERTY_SOCKET_OPEN) && ((SocketStatus)evt.getSource()).socketClosed()){
            // FIXME store socket references with ids
            System.out.println(connections.size() + " sockets in queue");
        }
    }
}
