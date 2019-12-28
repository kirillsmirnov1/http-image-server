package server;

import java.util.Scanner;

import static httpUtil.Constants.STOP;

public class ServerHandler {
    public static boolean slowServer = false;

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(8080);
        Thread serverThread = new Thread(httpServer);
        Scanner scanner = new Scanner(System.in);

        handleArgs(args);

        System.out.println("Starting server");

        serverThread.start();

        System.out.println("You can stop server with STOP command");

        while (true){
            String input = scanner.nextLine();

            if(input.toUpperCase().equals(STOP)){
                httpServer.stop();
                break;
            } else {
                System.out.println("Wrong input");
            }
        }

        while (httpServer.connections.size() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Server terminated");
    }

    private static void handleArgs(String[] args) {
        for(int i = 0; i < args.length; ++i){ // using basic for in case of adding arguments like ip and port
            if(args[i].equals("-slow")){
                slowServer = true;
            }
        }
    }
}
