package cz.cvut.fel.adaptiverestfulapi.security;

import cz.cvut.fel.adaptiverestfulapi.core.Context;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;


/**
 * Abstract authentication filter.
 */
public abstract class Authentication extends Filter {

    @Override
    public final void process(Context context) throws FilterException{
        if (this.authenticate(context)) {
            this.resign(context);

        } else {
            // TODO unauthorized context
        }
    }

    /**
     * Authenticate user in the context.
     * @param context
     * @return true if user is authenticated
     */
    protected abstract boolean authenticate(Context context);
}
