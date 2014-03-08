
package cz.cvut.fel.adaptiverestfulapi.core;


/**
 * Class that holds both HTTP request and response.
 */
public class HttpContext {

    private Object content;

    private HttpRouter router;

    // request
    private final HttpMethod method;
    private final HttpHeaders requestHeaders;
    private String requestContent;

    // response
    private HttpStatus status;
    private HttpHeaders responseHeaders;
    private String responseContent;

    public HttpContext(String uri, HttpMethod method, HttpHeaders headers, String content) {
        this.router = HttpRouter.createRouter(uri);
        this.method = method;
        this.requestHeaders = headers;
        this.requestContent = content;
    }

    public Object getContent() {
        return this.content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public HttpRouter getRouter() {
        return this.router;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public HttpHeaders getRequestHeaders() {
        return this.requestHeaders;
    }

    public String getRequestContent() {
        return this.requestContent;
    }

    public void response(HttpStatus status, HttpHeaders headers, String content) {
        this.status = status;
        this.responseHeaders = headers;
        this.responseContent = content;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public String getResponseContent() {
        return this.responseContent;
    }

}
