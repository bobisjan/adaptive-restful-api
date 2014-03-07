
package cz.cvut.fel.adaptiverestfulapi.security.basic;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaders;
import cz.cvut.fel.adaptiverestfulapi.security.Authentication;
import cz.cvut.fel.adaptiverestfulapi.security.AuthenticationException;

import java.util.AbstractMap;
import java.util.Map;


/**
 * Abstract HTTP basic authentication.
 */
public abstract class BasicAuthentication extends Authentication {

    private String realm;

    public BasicAuthentication() {
        this.realm = null;
    }

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
        HttpHeaders httpHeaders = httpContext.getRequestHeaders();
        String auth = httpHeaders.get(HttpHeaders.Authorization);

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
