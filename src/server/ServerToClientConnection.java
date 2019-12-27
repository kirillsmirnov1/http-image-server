package server;

import httpUtil.HttpRequestHeader;
import httpUtil.HttpRequestParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

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

            while(true) { // FIXME while(true)

                System.out.println("\nClient sent: \n");

                HttpRequestHeader header = HttpRequestParser.parseRequest(reader);

                switch (header.getMethod()) {
                    case POST:
                        System.out.println("Seems like a POST");
                        isr.read(new char[(int) header.getContentLength()], 0, (int) header.getContentLength());
                        // TODO handle post request
                        break;
                    case GET:
                        System.out.println("Seems like a GET");
                        // TODO handle get request
                        break;
                    case HEAD:
                        System.out.println("Seems like a HEAD");
                        // TODO handle head request
                        break;
                    default:
                        System.out.println("It doesn't look like anything to me");
                        // TODO handle unknown request
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
