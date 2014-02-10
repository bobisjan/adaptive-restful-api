
package cz.cvut.fel.adaptiverestfulapi.core;


import java.net.URL;
import java.util.Map;

/**
 * Class that holds both HTTP request and response.
 */
public class HttpContext {

    private String uri;
    private HttpMethod httpMethod;
    private Map<String, String> headers;
    private String content;

    public HttpContext(String uri, HttpMethod httpMethod, Map<String, String> headers, String content) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.headers = headers;
        this.content = content;
    }

    public String getUri() {
        return this.uri;
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getContent() {
        return this.content;
    }

    // output

    private HttpStatus httpStatusCode;

    public HttpStatus getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

}
