package httpUtil;

import static httpUtil.Constants.*;
import static httpUtil.Constants.CONTENT_LENGTH;

public class HttpRequestHeader {

    private HttpMethod method;
    private String fileName;
    private int contentLength;

    private String contentType = "";

    private String actualHeader;

    public HttpRequestHeader(HttpMethod method, String fileName, int contentLength) {
        this.method = method;
        this.fileName = fileName;
        this.contentLength = contentLength;
    }

    @Override
    public String toString() {
        switch (method){
            case POST:
                return POST + " /" + fileName + " " + HTTP_1_1 + "\r\n" +
                        CONTENT_TYPE + " " + parseType(fileName) + "\r\n" +
                        CONTENT_LENGTH + " " + contentLength + "\r\n" +
                        "\r\n";
            case GET:
                return GET + " /" + fileName + " " + HTTP_1_1 + "\r\n" +
                        // TODO add Host, accept and what else
                        "\r\n"; // Required blank line
        }
        return "null";
    }

    private static String parseType(String fileName) {

        switch(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase()){
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
    public int getContentLength() { return contentLength; }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getActualHeader() {
        return actualHeader;
    }

    public void setActualHeader(String actualHeader) {
        this.actualHeader = actualHeader;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
