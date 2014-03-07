
package cz.cvut.fel.adaptiverestfulapi.core;


/**
 * Enum for HTTP status codes.
 * <a href="http://en.wikipedia.org/wiki/List_of_HTTP_status_codes">http://en.wikipedia.org/wiki/List_of_HTTP_status_codes</a>
 */
public enum HttpStatus {

    // 2xx Success
    S_200(200, "OK"),
    S_201(201, "Created"),
    S_204(204, "No Content"),

    // 3xx Redirection
    S_304(304, "Not Modified"),

    // 4xx Client Error
    S_400(400, "Bad Request"),
    S_401(401, "Unauthorized"),
    S_403(403, "Forbidden"),
    S_404(404, "Not Found"),

    // 5xx Server Error
    S_500(500, "Internal Server Error");


    private final int code;
    private final String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.code + " " + this.message;
    }

}
