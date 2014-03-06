package cz.cvut.fel.adaptiverestfulapi.security;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;


/**
 * HTTP basic authentication.
 */
public class BasicAuthentication extends Authentication {

    @Override
    protected void authenticate(HttpContext httpContext) throws AuthenticationException {
        // TODO implement basic authentication
    }
}
