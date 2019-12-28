package httpUtil;

public class HttpResponseHeader {
    private HttpCode code;
    private int contentLength;

    String headerContents;

    public HttpCode getCode() {
        return code;
    }

    public void setCode(HttpCode code) {
        this.code = code;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getHeaderContents() {
        return headerContents;
    }

    public void setHeaderContents(String headerContents) {
        this.headerContents = headerContents;
    }
}
