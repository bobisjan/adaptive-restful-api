
package cz.cvut.fel.adaptiverestfulapi.core;


/**
 * Class that holds both HTTP request and response.
 */
public final class Context {

    // input

    private Method method;

    // output

    private int statusCode;

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return this.method;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
