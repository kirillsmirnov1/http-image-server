package httpUtil;

public class HttpRequestHeader {
    private HttpMethod method;
    private String fileName;
    private long contentLength;

    public HttpMethod getMethod() { return method; }
    public String getFileName() { return fileName; }
    public long getContentLength() { return contentLength; }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
}
