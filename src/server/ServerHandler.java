package server;

import java.util.Scanner;

import static httpUtil.Constants.STOP;

public class ServerHandler {
    public static boolean slowServer = true;

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(8080);
        Thread serverThread = new Thread(httpServer);

        Scanner scanner = new Scanner(System.in);

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

        // TODO wait for all sockets to close
    }
}
