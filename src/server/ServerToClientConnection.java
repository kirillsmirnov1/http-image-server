package server;

import httpUtil.HttpRequestHeader;
import httpUtil.HttpRequestParser;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import static httpUtil.Constants.*;
import static httpUtil.HttpResponseParser.prepareJSONFileName;
import static httpUtil.HttpResponseParser.prepareResponseHeader;

public class ServerToClientConnection implements Runnable {

    private Socket socket;

    private boolean keepConnectionToAClient = true;

    private InputStream inputStream;
    private OutputStream outputStream;

    ServerToClientConnection(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            outputStream = socket.getOutputStream();

            inputStream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);

            while(keepConnectionToAClient) {

                HttpRequestHeader header = HttpRequestParser.parseRequest(reader);

                if(header == null){
                    break;
                }

                System.out.println("\nClient sent: \n");

                switch (header.getMethod()) {
                    case POST:
                        handlePostRequest(header);
                        break;
                    case GET:
                        handleGetRequest(header);
                        break;
                    case HEAD:
                        System.out.println("Seems like a HEAD");
                        // TODO handle head request
                        break;
                    default:
                        handleUnknownRequest(header);
                }
            }

            closeConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUnknownRequest(HttpRequestHeader header) {
        System.out.println(header.getHeaderContents());
        sendResponseHeader(prepareResponseHeader(NOT_ALLOWED_405));
    }

    private void handleGetRequest(HttpRequestHeader header) {

        System.out.println(header.getHeaderContents());

        File file = new File(header.getFileName());

        if(file.exists()){
            sendResponseHeader(prepareResponseHeader(OK_200, (int)file.length()));
            try {
                Files.copy(file.toPath(), outputStream);
            } catch (IOException e) {
                System.out.println("Couldn't write file to client");
                closeConnection();
            }
        } else {
            sendResponseHeader(prepareResponseHeader(NOT_FOUND_404));
        }
    }

    private void closeConnection() {
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        keepConnectionToAClient = false;
    }

    private void handlePostRequest(HttpRequestHeader header) {
        System.out.println(header.getHeaderContents());

        byte[] bytes = new byte[header.getContentLength()];

        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            System.out.println("Couldn't read file from client");
            closeConnection();
            return;
        }

        try {
            File file = new File(header.getFileName());

            new FileOutputStream(file).write(bytes);

            System.out.println("File saved");

        } catch (IOException e) {
            System.out.println("Couldn't save file");
            sendResponseHeader(prepareResponseHeader(SERVER_ERROR_500));
            return;
        }

        String jsonResponse = prepareJSONFileName(header.getFileName());

        sendResponseHeader(prepareResponseHeader(OK_200, jsonResponse.length()));

        try {
            outputStream.write(jsonResponse.getBytes());
            System.out.println("Response send");
        } catch (IOException e) {
            System.out.println("Couldn't response to client");
            closeConnection();
        }
    }

    private void sendResponseHeader(String response) {
        try {
            outputStream.write(response.getBytes());
        } catch (IOException e) {
            System.out.println("Couldn't send a response");
            closeConnection();
        }
    }
}
