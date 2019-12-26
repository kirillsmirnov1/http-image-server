package server;

public class HttpServer {

    private final String address;
    private final int port;

    HttpServer(String address, int port){
        this.address = address;
        this.port = port;
    }

    public void run(){
        System.out.println("Server started, but that ain't actually true");
        // TODO start a thread which checks input connections
    }

    public void stop(){
        System.out.println("Server stopped, I guess");
        // TODO
    }
}
