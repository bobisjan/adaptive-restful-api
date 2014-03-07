
package cz.cvut.fel.adaptiverestfulapi.core;


public class FilterException extends Exception {

    protected HttpStatus status;
    protected HttpHeaders headers;

    public FilterException() {
        this("Something gets broken during the filter process.");
    }

    public FilterException(HttpStatus status) {
        this(status, new HttpHeaders());
    }

    public FilterException(HttpStatus status, HttpHeaders headers) {
        this(status.getMessage(), status, headers);
    }

    public FilterException(String message) {
        this(message, HttpStatus.S_500, new HttpHeaders());
    }

    public FilterException(String message, HttpStatus status, HttpHeaders headers) {
        super(message);
        this.status = status;
        this.headers = headers;
    }

    public FilterException(Exception e) {
        super(e);
        this.status = HttpStatus.S_500;
        this.headers = new HttpHeaders();
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

}
