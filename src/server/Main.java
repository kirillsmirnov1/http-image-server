package server;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(8080);
        Thread serverThread = new Thread(httpServer);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Starting server");

        serverThread.start();

        System.out.println("You can stop server with STOP command");

        while (true){
            String input = scanner.nextLine();

            if(input.toUpperCase().equals("STOP")){ // TODO enum with keys?
                httpServer.stop(); // TODO wait for server to stop
                break;
            } else {
                System.out.println("Wrong input");
            }
        }
    }
}
