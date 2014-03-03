package cz.cvut.fel.adaptiverestfulapi.security;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


/**
 * Abstract authentication filter.
 */
public abstract class Authentication extends Filter {

    @Override
    public final HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        if (this.authenticate(httpContext)) {
            return this.resign(httpContext, model, configuration);

        } else {
            // TODO unauthorized httpContext
            return httpContext;
        }
    }

    /**
     * Authenticate user in the httpContext.
     * @param httpContext
     * @return true if user is authenticated
     */
    protected abstract boolean authenticate(HttpContext httpContext);

}
