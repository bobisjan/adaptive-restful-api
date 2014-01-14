
package cz.fel.cvut.adaptiverestfulapi.core;


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
     * Resigns processing of the context to the next filter in the chain.
     * @param context
     * @throws FilterException
     */
    public final void resign(Context context) throws FilterException {
        if (this.next != null) {
            this.next.process(context);
        }
    }

    /**
     * Processes filter to change the context.
     * @param context
     * @throws FilterException
     */
    public abstract void process(Context context) throws FilterException;

}
