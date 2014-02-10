
package cz.cvut.fel.adaptiverestfulapi.core;


/**
 * Abstract filter responsible for processing with context.
 */
public abstract class Filter {

    private Filter next; // in the chain

    public Filter() {
        this(null);
    }

    public Filter(Filter next) {
        this.setNext(next);
    }

    protected void setNext(Filter next) {
        this.next = next;
    }

    /**
     * Resigns processing of the httpContext to the next filter in the chain.
     * @param httpContext
     * @throws FilterException
     */
    protected final void resign(HttpContext httpContext) throws FilterException {
        if (this.next != null) {
            this.next.process(httpContext);
        }
    }

    /**
     * Processes filter to change the httpContext.
     * @param httpContext
     * @throws FilterException
     */
    public abstract void process(HttpContext httpContext) throws FilterException;

}
