
package cz.cvut.fel.adaptiverestfulapi.core;


/**
 * Class that holds both HTTP request and response.
 */
public final class Context {

    // HTTP methods
    public static final String GET    = "GET";
    public static final String POST   = "POST";
    public static final String PUT    = "PUT";
    public static final String DELETE = "DELETE";

    // input

    private String method;

    // output

    private int statusCode;

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
