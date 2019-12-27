package client;

public class HttpClient {
    private final int port;

    HttpClient(int port){
        this.port = port;
    }

    // Should I call it in send/get methods or while creating a client?
    public void connectToServer(){
        System.out.println("Connecting server with port: " + port);
        //TODO
    }

    public void sendPostRequest(String filename){
        System.out.println("Should send post request for " + filename);
        // TODO
    }

    public void sendGetRequest(String filename){
        System.out.println("Should send get request for " + filename);
        // TODO
    }

    public void stop() {
        System.out.println("Should destroy connection to server");
        // TODO
    }
}
