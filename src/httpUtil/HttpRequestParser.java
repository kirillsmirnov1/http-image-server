package httpUtil;

import java.io.BufferedReader;
import java.io.IOException;

import static httpUtil.Constants.*;

public class HttpRequestParser {

    public static HttpRequestHeader parseRequest(BufferedReader reader) {
        try {
            HttpRequestHeader header = new HttpRequestHeader();
            StringBuilder headerContents = new StringBuilder();

            String line = reader.readLine();

            while(!line.isBlank()){
                headerContents.append(line).append("\r\n");
                String[] tokens = line.split(" ");

                switch (tokens[0]){
                    case POST: // FIXME it would be better to use enum here, but POST.name() doesn't work here and I couldn't think of anything else
                    case GET:
                    case HEAD: {
                        header.setMethod(HttpMethod.valueOf(tokens[0]));

                        // Implying that we are storing all files in server root
                        header.setFileName(tokens[1].substring(tokens[1].lastIndexOf("/") + 1)); // TODO might need to check if there is actually something in there
                        break;
                    }
                    case CONTENT_LENGTH: {
                        header.setContentLength(Integer.parseInt(tokens[1]));
                    }
                }
                line = reader.readLine();
            }

            header.setHeaderContents(headerContents.toString());

            return header;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("Connection seems to be broken");
        }
        return null;
    }


    public static String prepareGetRequest(String fileName){

        return GET + " " + fileName + " " + HTTP_1_1 + "\r\n" +
                // TODO add Host, accept and what else
                "\r\n"; // Required blank line
    }

    public static String preparePostRequestHeader(String fileName, long contentLength){

        return POST + " /" + fileName + " " + HTTP_1_1 + "\r\n" +
                CONTENT_TYPE + " " + parseType(fileName) + "\r\n" +
                CONTENT_LENGTH + " " + contentLength + "\r\n" +
                "\r\n";
    }

    private static String parseType(String fileName) {

        switch(fileName.replaceAll("^[^.]*.", "").toLowerCase()){ // FIXME that actually catches only first dot
            case "jpg":
            case "jpeg":
                return IMAGE_JPEG;
            case "bmp":
                return IMAGE_BMP;
            case "gif":
                return IMAGE_GIF;
            case "png":
                return IMAGE_PNG;
            default:
                return APPLICATION_OCTET_STREAM;
        }
    }
}
