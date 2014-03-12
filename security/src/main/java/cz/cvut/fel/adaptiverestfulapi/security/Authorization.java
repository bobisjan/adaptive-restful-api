
package cz.cvut.fel.adaptiverestfulapi.security;

import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


public abstract class Authorization extends Filter {

    @Override
    public final HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        this.authorize(httpContext, model, configuration);
        return this.resign(httpContext, model, configuration);
    }

    /**
     * Authorizes HTTP context.
     * @param httpContext The HTTP context.
     * @throws AuthorizationException
     */
    protected abstract void authorize(HttpContext httpContext, Model model, Configuration configuration) throws AuthorizationException;

}
