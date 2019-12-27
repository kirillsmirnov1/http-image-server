package httpUtil;

public class HttpRequestHeader {

    private HttpMethod method;
    private String fileName;
    private int contentLength;

    private String headerContents;

    public HttpMethod getMethod() { return method; }
    public String getFileName() { return fileName; }
    public int getContentLength() { return contentLength; }
    public String getHeaderContents() { return headerContents; }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setHeaderContents(String headerContents) {
        this.headerContents = headerContents;
    }
}
