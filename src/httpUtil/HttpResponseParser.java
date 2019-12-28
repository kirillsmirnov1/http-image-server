package httpUtil;

import static httpUtil.Constants.CONTENT_LENGTH;
import static httpUtil.Constants.HTTP_1_1;

public class HttpResponseParser {


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
        return "{\r\n\t\"file\": \"" + fileName + "\"\r\n}";
    }
}
