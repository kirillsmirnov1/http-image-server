package httpUtil;

public interface Constants {

    // HTTP methods, also used as client commands
    String POST = "POST";
    String GET = "GET";
    String HEAD = "HEAD";

    // Other client commands
    String STOP = "STOP";

    // HTTP fields
    String CONTENT_LENGTH = "Content-Length:";
    String CONTENT_TYPE = "Content-Type:";

    // Other HTTP constants
    String HTTP_1_1 = "HTTP/1.1";

    // mime-types
    String IMAGE_JPEG = "image/jpeg";
    String IMAGE_BMP = "image/bmp";
    String IMAGE_GIF = "image/gif";
    String IMAGE_PNG = "image/png";
    String APPLICATION_OCTET_STREAM = "application/octet-stream";
}
