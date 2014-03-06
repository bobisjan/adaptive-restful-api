package cz.cvut.fel.adaptiverestfulapi.caching;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


/**
 * Abstract class for cache support.
 */
public abstract class Cache extends Filter {

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        if (this.load(httpContext)) { // try load data from cache
            return httpContext;
        }

        httpContext = this.resign(httpContext, model, configuration);  // ask for data someone else
        this.save(httpContext);    // finally save new data to cache
        return httpContext;
    }

    /**
     * Load data from the cache.
     * @param httpContext
     * @return true if hit was made
     */
    protected abstract boolean load(HttpContext httpContext);

    /**
     * Save data to the cache.
     * @param httpContext
     */
    protected abstract void save(HttpContext httpContext);

}
