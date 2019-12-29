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
    String CONTENT_DISPOSITION = "Content-Disposition:";

    // Other HTTP constants
    String HTTP_1_1 = "HTTP/1.1";
    String MULTIPART = "multipart/form-data;";

    // mime-types
    String IMAGE_JPEG = "image/jpeg";
    String IMAGE_BMP = "image/bmp";
    String IMAGE_GIF = "image/gif";
    String IMAGE_PNG = "image/png";
    String APPLICATION_OCTET_STREAM = "application/octet-stream";

    // Response codes
    String OK_200 = "200 OK";
    String NOT_FOUND_404 = "404 Not Found";
    String NOT_ALLOWED_405 = "405 Method Not Allowed";
    String SERVER_ERROR_500 = "500 Internal Server Error";
}
