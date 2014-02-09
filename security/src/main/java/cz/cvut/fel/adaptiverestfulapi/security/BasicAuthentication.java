package cz.cvut.fel.adaptiverestfulapi.security;

import cz.cvut.fel.adaptiverestfulapi.core.Context;


/**
 * HTTP basic authentication.
 */
public class BasicAuthentication extends Authentication {

    @Override
    protected boolean authenticate(Context context) {
        return true; // TODO implement
    }
}
