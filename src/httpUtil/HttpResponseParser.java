package httpUtil;

import java.io.BufferedReader;
import java.io.IOException;

import static httpUtil.Constants.CONTENT_LENGTH;
import static httpUtil.Constants.HTTP_1_1;

public class HttpResponseParser {

    public static HttpResponseHeader parseResponse(BufferedReader reader){

        try {
            HttpResponseHeader header = new HttpResponseHeader();
            StringBuilder headerContents = new StringBuilder();

            String line = reader.readLine();

            while (!line.isEmpty()){
                headerContents.append(line).append("\n");
                String[] tockens = line.split(" ");

                switch (tockens[0]){
                    case HTTP_1_1:
                        header.setCode(HttpCode.codeOf(tockens[1]));
                        break;
                    case CONTENT_LENGTH:
                        header.setContentLength(Integer.parseInt(tockens[1]));
                        break;
                }

                line = reader.readLine();
            }

            header.setHeaderContents(headerContents.toString());

            return header;

        } catch (IOException e) {
            System.out.println("Couldn't read response header");
        }
        return null;
    }

    public static String prepareResponseHeader(String statusCode) {
        return prepareResponseHeader(statusCode, -1);
    }

    public static String prepareResponseHeader(String statusCode, int length) {
        StringBuilder str = new StringBuilder();
        str.append(HTTP_1_1).append(" ").append(statusCode).append("\r\n");

        if(length >= 0){
            str.append(CONTENT_LENGTH).append(" ").append(length).append("\r\n");
        }

        str.append("\r\n");

        return str.toString();
    }

    public static String prepareJSONFileName(String fileName) {
        return "{\r\n\t\"file\": \"" + fileName + "\"\r\n}\r\n\r\n";
    }
}
