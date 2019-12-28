package client;

import java.io.File;
import java.util.Scanner;

import static httpUtil.Constants.*;

public class ClientHandler {

    private static boolean keepConnection = true;

    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient(8080);

        while(keepConnection) {
            httpClient.connectToServer(); // FIXME handle server being offline

            handleInput(httpClient);

            handleDisconnect();
        }
    }

    private static void handleDisconnect() {
        if(keepConnection) {
            System.out.print("Connection seems to be lost. Wanna reconnect? [Y/N]: ");
            keepConnection = new Scanner(System.in).nextLine().matches("[Yy]]*");
        }
    }

    private static void handleInput(HttpClient httpClient) {
        Scanner scanner = new Scanner(System.in);

        while (httpClient.isConnected()){
            System.out.println("\nYou can use POST [filename], GET [filename] and STOP commands\n");
            String[] input = scanner.nextLine().split(" ");

            switch (input[0].toUpperCase()){
                case POST:
                    if(filenameIsFine(input)){
                        httpClient.sendPostRequest(input[1]);
                    }
                    break;
                case GET:
                    if(filenameIsFine(input)){
                        httpClient.sendGetRequest(input[1]);
                    }
                    break;
                case STOP:
                    httpClient.stop();
                    keepConnection = false;
                    return;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    private static boolean filenameIsFine(String[] input) {

        if(input.length < 2){
            System.out.println("No filename");
            return false;
        }

        if(input[0].toUpperCase().equals(POST)){
            File file = new File(input[1]);
            if(!file.exists()){
                System.out.println("File doesn't exist");
                return false;
            }
        }

        return true;
    }
}
