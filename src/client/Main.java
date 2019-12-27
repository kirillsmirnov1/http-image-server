package client;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient(8080);
        httpClient.connectToServer();

        handleInput(httpClient);
    }

    private static void handleInput(HttpClient httpClient) {
        System.out.println("\nYou can use POST [filename], GET [filename] and STOP commands\n");
        Scanner scanner = new Scanner(System.in);

        while (true){
            String[] input = scanner.nextLine().split(" ");
            switch (input[0].toUpperCase()){    // TODO enum with keys?
                case "POST":
                    if(filenameIsFine(input)){
                        httpClient.sendPostRequest(input[1]);
                    }
                    break;
                case "GET":
                    if(filenameIsFine(input)){
                        httpClient.sendGetRequest(input[1]);
                    }
                    break;
                case "STOP":
                    httpClient.stop();
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

        if(input[0].toUpperCase().equals("POST")){
            File file = new File(input[1]);
            if(!file.exists()){
                System.out.println("File doesn't exist");
                return false;
            }
        }

        return true;
    }
}
