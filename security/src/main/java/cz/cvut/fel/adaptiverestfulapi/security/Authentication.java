package cz.cvut.fel.adaptiverestfulapi.security;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;


/**
 * Abstract authentication filter.
 */
public abstract class Authentication extends Filter {

    @Override
    public final void process(HttpContext httpContext) throws FilterException {
        if (this.authenticate(httpContext)) {
            this.resign(httpContext);

        } else {
            // TODO unauthorized httpContext
        }
    }

    /**
     * Authenticate user in the httpContext.
     * @param httpContext
     * @return true if user is authenticated
     */
    protected abstract boolean authenticate(HttpContext httpContext);

}
