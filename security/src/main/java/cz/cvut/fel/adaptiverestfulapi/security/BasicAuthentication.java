package cz.cvut.fel.adaptiverestfulapi.security;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;

import java.util.AbstractMap;
import java.util.Map;


/**
 * Abstract HTTP basic authentication.
 */
public abstract class BasicAuthentication extends Authentication {

    private String realm;

    public BasicAuthentication(String realm) {
        this.realm = realm;
    }

    @Override
    protected final void authenticate(HttpContext httpContext) throws AuthenticationException {
        Map.Entry<String, String> user = this.user(httpContext);

        if (!this.isAuthenticated(user.getKey(), user.getValue())) {
            throw new BasicAuthenticationException(this.realm);
        }
    }

    /**
     * Returns whether the current user is authenticated.
     * @param username The user's name.
     * @param password The user's password.
     * @return
     */
    protected abstract boolean isAuthenticated(String username, String password);

    private Map.Entry<String, String> user(HttpContext httpContext) throws AuthenticationException {
        // TODO Get rid off the string, use constant
        String auth = httpContext.getRequestHeaders().get("authorization");

        if (auth == null || !auth.startsWith("Basic ")) {
            throw new BasicAuthenticationException(this.realm);
        }

        auth = auth.substring("Basic ".length());
        auth = new String(Base64.decode(auth));

        String[] parts = auth.split(":");

        if (parts.length != 2) {
            throw new BasicAuthenticationException(this.realm);
        }
        return new AbstractMap.SimpleImmutableEntry<String, String>(parts[0], parts[1]);
    }

}
