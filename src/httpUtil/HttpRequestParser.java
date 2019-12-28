package httpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

import static httpUtil.Constants.*;

public class HttpRequestParser {

    public static HttpRequestHeader parseRequest(BufferedReader reader) {
        try {
            HttpRequestHeader header = new HttpRequestHeader(null, null, -1);

            String line = reader.readLine();

            while(!line.isEmpty()){
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

            return header;
        } catch (SocketException | NullPointerException e){
            System.out.println("Connection to client seems to be closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
