package server;

import httpUtil.HttpMethod;
import httpUtil.HttpRequestHeader;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;

import static httpUtil.Constants.*;
import static httpUtil.HttpResponseParser.prepareJSONFileName;
import static httpUtil.HttpResponseParser.prepareResponseHeader;
import static server.SocketStatus.PROPERTY_ACTIVE_TRANSACTION;

public class ServerToClientConnection implements Runnable, PropertyChangeListener {

    private Socket socket;

    private boolean keepConnectionToAClient = true;

    private InputStream inputStream;
    private OutputStream outputStream;

    private SocketStatus socketStatus = new SocketStatus();

    ServerToClientConnection(Socket socket, int id){
        this.socket = socket;
        socketStatus.setId(id);

        socketStatus.addPropertyChangeListener("activeTransaction", this);
    }

    @Override
    public void run() {
        try{
            outputStream = socket.getOutputStream();

            inputStream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);

            while(keepConnectionToAClient) {

                HttpRequestHeader header = new HttpRequestParser().parseRequest(reader);

                if(header == null){
                    break;
                }

                System.out.println("\nClient sent: \n");
                System.out.println(header.getActualHeader());

                if(ServerHandler.slowServer){
                    try {
                        System.out.println("Intentionally slowing server down");
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

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

                socketStatus.setActiveTransaction(false);
            }

            closeConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUnknownRequest(HttpRequestHeader header) {
        System.out.println(header.toString());
        sendResponseHeader(prepareResponseHeader(NOT_ALLOWED_405));
    }

    private void handleGetRequest(HttpRequestHeader header) {

        if(header.getFileName().equals("")){
            header.setFileName("index.html");
        }

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
        keepConnectionToAClient = false;
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketStatus.setSocketOpen(false);
    }

    private void handlePostRequest(HttpRequestHeader header) {

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

    public void timeToCloseConnection(){
        keepConnectionToAClient = false;
        if(socketStatus.transactionIsNotActive()){ // If it is active, connection will be closed on update() call
            closeConnection();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(PROPERTY_ACTIVE_TRANSACTION)){
            if(!keepConnectionToAClient && socketStatus.transactionIsNotActive()){
                closeConnection();
            }
        }
    }

    public SocketStatus getSocketStatus() {
        return socketStatus;
    }

    private class HttpRequestParser {

        public HttpRequestHeader parseRequest(BufferedReader reader) {
            try {
                HttpRequestHeader header = new HttpRequestHeader(null, null, -1);
                StringBuilder str = new StringBuilder();

                String line = reader.readLine();

                socketStatus.setActiveTransaction(true);



                while(!line.isEmpty()){
                    String[] tokens = line.split(" ");

                    str.append(line).append("\r\n");

                    switch (tokens[0]){
                        case POST: // FIXME it would be better to use enum here, but POST.name() doesn't work here and I couldn't think of anything else
                        case GET:
                        case HEAD: {
                            header.setMethod(HttpMethod.valueOf(tokens[0]));

                            String filename;
                            if(tokens[1].contains("/?")){
                                filename = tokens[1].substring(tokens[1].lastIndexOf("=") + 1);
                            } else { // FIXME I should send correct request
                                filename = tokens[1].substring(tokens[1].lastIndexOf("/") + 1);
                            }

                            // Implying that we are storing all files in server root
                            header.setFileName(filename); // TODO might need to check if there is actually something in there
                            break;
                        }
                        case CONTENT_LENGTH: {
                            header.setContentLength(Integer.parseInt(tokens[1]));
                        }
                    }
                    line = reader.readLine();
                }

                header.setActualHeader(str.toString());

                return header;
            } catch (SocketException | NullPointerException e){
                System.out.println("Connection to client seems to be closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
