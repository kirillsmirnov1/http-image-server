package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

public class ClientConnection implements Runnable {

    private Socket socket;

    ClientConnection(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try(InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(isr)){                               // TODO add output stream

            System.out.println("\nClient sent: \n");

            String line = reader.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }

            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + new Date();

            System.out.println("\nWe are sending: \n" + httpResponse);

            socket.getOutputStream().write(httpResponse.getBytes());

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
