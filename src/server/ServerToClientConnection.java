package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class ServerToClientConnection implements Runnable {

    private Socket socket;

    ServerToClientConnection(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try(InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            OutputStream outputStream = socket.getOutputStream()){

            System.out.println("\nClient sent: \n");

            String line = reader.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }

            String httpResponse = "HTTP/1.1 200 OK\r\nConnection: close\r\n\r\n" + new Date();

            System.out.println("\nWe are sending: \n" + httpResponse);

            outputStream.write(httpResponse.getBytes());

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}