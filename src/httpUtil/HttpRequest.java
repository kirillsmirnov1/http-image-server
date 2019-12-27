package httpUtil;

import java.io.BufferedReader;
import java.io.IOException;

import static httpUtil.Constants.*;

public class HttpRequest {
    private HttpMethod method;
    private String fileName;
    private long contentLength;

    HttpRequest(BufferedReader reader){
        parseRequest(reader);
    }

    private void parseRequest(BufferedReader reader) {
        try {
            String line = reader.readLine();

            while(!line.isEmpty()){ // FIXME does /r/n goes as empty line?
                String[] tokens = line.split(" ");

                switch (tokens[0]){
                    case POST:
                    case GET:
                    case HEAD: {
                        method = HttpMethod.valueOf(tokens[0]);
                        fileName = tokens[1];
                        break;
                    }
                    case CONTENT_LENGTH: {
                        contentLength = Long.parseLong(tokens[1]);
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String prepareGetRequest(){
        // TODO
        return null;
    }

    public String preparePostRequest(){
        // TODO
        return null;
    }

    public HttpMethod getMethod() { return method; }
    public String getFileName() { return fileName; }
    public long getContentLength() { return contentLength; }
}
