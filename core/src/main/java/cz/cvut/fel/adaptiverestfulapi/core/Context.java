
package cz.cvut.fel.adaptiverestfulapi.core;


/**
 * Class that holds both HTTP request and response.
 */
public final class Context {

    // input

    private Method method;

    // output

    private Status statusCode;

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return this.method;
    }

    public Status getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Status statusCode) {
        this.statusCode = statusCode;
    }

}
