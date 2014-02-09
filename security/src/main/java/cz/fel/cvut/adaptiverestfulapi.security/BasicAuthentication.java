package cz.fel.cvut.adaptiverestfulapi.security;

import cz.fel.cvut.adaptiverestfulapi.core.Context;
import cz.fel.cvut.adaptiverestfulapi.security.Authentication;


/**
 * HTTP basic authentication.
 */
public class BasicAuthentication extends Authentication {

    @Override
    protected boolean authenticate(Context context) {
        return true; // TODO implement
    }
}
