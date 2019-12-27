package server;

import httpUtil.HttpRequestHeader;
import httpUtil.HttpRequestParser;

import java.io.*;
import java.net.Socket;

public class ServerToClientConnection implements Runnable {

    private Socket socket;

    ServerToClientConnection(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try(InputStream inputStream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            OutputStream outputStream = socket.getOutputStream()){

            while(true) { // FIXME while(true)

                HttpRequestHeader header = HttpRequestParser.parseRequest(reader);

                if(header == null){
                    socket.close();
                    break;
                }

                System.out.println("\nClient sent: \n");

                switch (header.getMethod()) {
                    case POST:
                        System.out.println(header.getHeaderContents());

                        File file = new File(header.getFileName());

                        byte[] bytes = new byte[header.getContentLength()];

                        inputStream.read(bytes);

                        new FileOutputStream(file).write(bytes);

                        System.out.println("File saved");

                        // TODO handle response
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
