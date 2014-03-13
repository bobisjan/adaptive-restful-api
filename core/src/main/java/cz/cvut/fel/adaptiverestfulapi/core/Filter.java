
package cz.cvut.fel.adaptiverestfulapi.core;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


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

    public Filter setNext(Filter next) {
        this.next = next;
        return this;
    }

    public Filter setLast(Filter last) {
        Filter next = this.next;

        if (next == null) {
            this.setNext(last);
            return this;
        }

        while (next != null) {
            if (next.next != null) {
                next = next.next;

            } else {
                next.setNext(last);
            }
        }
        return this;
    }

    /**
     * Resigns processing of the httpContext to the next filter in the chain.
     * @param context to resign
     * @param model model
     * @param configuration configuration
     * @return resigned context
     * @throws FilterException
     */
    protected final HttpContext resign(HttpContext context, Model model, Configuration configuration) throws FilterException {
        if (this.next != null) {
            return this.next.process(context, model, configuration);
        }
        return context;
    }

    /**
     * Processes filter to change the httpContext.
     * @param context to filter
     * @param model model
     * @param configuration configuration
     * @return filtered context
     * @throws FilterException
     */
    public abstract HttpContext process(HttpContext context, Model model, Configuration configuration) throws FilterException;

}
