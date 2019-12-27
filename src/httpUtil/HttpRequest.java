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

            while(!line.isBlank()){
                String[] tokens = line.split(" ");

                switch (tokens[0]){
                    case POST: // FIXME it would be better to use enum here, but POST.name() doesn't work here and I couldn't think of anything else
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


    public String prepareGetRequest(String fileName){

        return GET + " " + fileName + " " + HTTP_1_1 + "\r\n" +
                // TODO add Host, accept and what else
                "\r\n"; // Required blank line
    }

    public String preparePostRequest(){
        // TODO
        return null;
    }

    private String parseType(String fileName) {

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

    public HttpMethod getMethod() { return method; }
    public String getFileName() { return fileName; }
    public long getContentLength() { return contentLength; }
}
