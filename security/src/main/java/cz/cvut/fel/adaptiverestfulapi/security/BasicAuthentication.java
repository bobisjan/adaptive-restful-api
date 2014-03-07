package cz.cvut.fel.adaptiverestfulapi.security;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;


/**
 * HTTP basic authentication.
 */
public class BasicAuthentication extends Authentication {

    private Map<String, String> users;

    public BasicAuthentication() {
        this(new HashMap<String, String>());
    }

    public BasicAuthentication(Map<String, String> users) {
        this.users = new HashMap<>();
        for (Map.Entry<String, String> user : users.entrySet()) {
            this.users.put(user.getKey(), user.getValue());
        }
    }

    @Override
    protected void authenticate(HttpContext httpContext) throws AuthenticationException {
        Map.Entry user = this.user(httpContext);

        if (!this.users.containsKey(user.getKey()) || !this.users.get(user.getKey()).equals(user.getValue())) {
            throw new AuthenticationException();
        }
    }

    private Map.Entry<String, String> user(HttpContext httpContext) throws AuthenticationException {
        // TODO Get rid off the string, use constant
        String auth = httpContext.getRequestHeaders().get("Authorization");

        if (!auth.startsWith("Basic ")) {
            throw new AuthenticationException();
        }

        auth = auth.substring("Basic ".length());
        auth = new String(Base64.decode(auth));

        String[] parts = auth.split(":");

        if (parts.length != 2) {
            throw new AuthenticationException();
        }
        return new AbstractMap.SimpleImmutableEntry<String, String>(parts[0], parts[1]);
    }

}
