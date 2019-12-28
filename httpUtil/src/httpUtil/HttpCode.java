package httpUtil;

public enum HttpCode {
    OK_200,
    NOT_FOUND_404,
    NOT_ALLOWED_405,
    SERVER_ERROR_500;

    // FIXME there might be a better way
    public static HttpCode codeOf(String code){
        switch (code){
            case "200" : return OK_200;
            case "404" : return NOT_FOUND_404;
            case "405" : return NOT_ALLOWED_405;
            case "500" : return SERVER_ERROR_500;
            default    : return null;
        }
    }
}
