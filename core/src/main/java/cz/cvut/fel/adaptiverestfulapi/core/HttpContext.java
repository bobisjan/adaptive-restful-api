
package cz.cvut.fel.adaptiverestfulapi.core;


import java.net.URL;
import java.util.Map;

/**
 * Class that holds both HTTP request and response.
 */
public final class HttpContext {

    private URL url;
    private HttpMethod httpMethod;
    private Map<String, String> headers;
    private String content;

    public HttpContext(URL url, HttpMethod httpMethod, Map<String, String> headers, String content) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.headers = headers;
        this.content = content;
    }

    public URL getUrl() {
        return this.url;
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

    private Status statusCode;

    public Status getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Status statusCode) {
        this.statusCode = statusCode;
    }

}
