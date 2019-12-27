package client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient(8080);
        httpClient.connectToServer();

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nYou can use POST [filename], GET [filename] and STOP commands\n");

        whileLoop: while (true){ // TODO maybe encapsulate that while?
            String[] input = scanner.nextLine().split(" ");
            switch (input[0].toUpperCase()){    // TODO enum with keys?
                case "POST":
                    httpClient.sendPostRequest(input[1]); // TODO check bounds
                    break;
                case "GET":
                    httpClient.sendGetRequest(input[1]);
                    break;
                case "STOP":
                    httpClient.stop();
                    break whileLoop;
                default:
                    System.out.println("Wrong input");
            }
        }

    }
}
