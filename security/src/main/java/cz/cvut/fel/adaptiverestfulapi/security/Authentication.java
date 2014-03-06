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
        this.authenticate(httpContext);
        return this.resign(httpContext, model, configuration);
    }

    /**
     * Authenticates user in the HTTP context.
     * @param httpContext The HTTP context.
     * @throws AuthenticationException
     */
    protected abstract void authenticate(HttpContext httpContext) throws AuthenticationException;

}
