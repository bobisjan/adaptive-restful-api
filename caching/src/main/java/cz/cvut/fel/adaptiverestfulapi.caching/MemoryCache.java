
package cz.cvut.fel.adaptiverestfulapi.caching;

import cz.fel.cvut.adaptiverestfulapi.core.Context;

/**
 * In memory cache.
 */
public class MemoryCache extends Cache {

    @Override
    public boolean load(Context context) {
        return false;
    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void clear(Context context) {

    }

}
