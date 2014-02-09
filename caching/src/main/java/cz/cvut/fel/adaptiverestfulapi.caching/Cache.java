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
        if (!this.load(context)) { // try load data from cache
            this.resign(context);  // ask for data someone else
            this.save(context);    // finally save new data to cache
        }
    }

    /**
     * Load data from the cache.
     * @param context
     * @return true if hit was made
     */
    protected abstract boolean load(Context context);

    /**
     * Save data to the cache.
     * @param context
     */
    protected abstract void save(Context context);

}
