
package cz.cvut.fel.adaptiverestfulapi.caching;

import cz.cvut.fel.adaptiverestfulapi.core.Context;

/**
 * In memory cache.
 */
public class MemoryCache extends Cache {

    @Override
    protected boolean load(Context context) {
        return false;
    }

    @Override
    protected void save(Context context) {

    }

    @Override
    protected void clear(Context context) {

    }

}
