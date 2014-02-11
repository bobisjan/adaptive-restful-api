
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
     * @param context to resign
     * @return resigned context
     * @throws FilterException
     */
    protected final HttpContext resign(HttpContext context) throws FilterException {
        if (this.next != null) {
            return this.next.process(context);
        }
        return context;
    }

    /**
     * Processes filter to change the httpContext.
     * @param context to filter
     * @return filtered context
     * @throws FilterException
     */
    public abstract HttpContext process(HttpContext context) throws FilterException;

}
