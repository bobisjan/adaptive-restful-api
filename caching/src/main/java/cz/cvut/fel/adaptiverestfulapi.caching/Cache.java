package cz.cvut.fel.adaptiverestfulapi.caching;

import cz.cvut.fel.adaptiverestfulapi.core.Context;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;


/**
 * Abstract class for cache support.
 */
public abstract class Cache extends Filter {

    @Override
    public void process(Context context) throws FilterException {
        this.resign(context);
    }

    /**
     * Load data from the cache.
     * @param context
     * @return true if hit was made
     */
    public abstract boolean load(Context context);

    /**
     * Save data to the cache.
     * @param context
     */
    public abstract void save(Context context);

    /**
     * Remove data from the cache.
     * @param context
     */
    public abstract void clear(Context context);

}
