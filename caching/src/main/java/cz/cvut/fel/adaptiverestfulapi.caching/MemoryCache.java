
package cz.cvut.fel.adaptiverestfulapi.caching;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;

/**
 * In memory cache.
 */
public class MemoryCache extends Cache {

    @Override
    protected boolean load(HttpContext httpContext) {
        return false;
    }

    @Override
    protected void save(HttpContext httpContext) {

    }

}
