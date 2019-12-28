package httpUtil;

public class HttpResponseHeader {
    private String code;
    private int contentLength;

    String headerContents;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
