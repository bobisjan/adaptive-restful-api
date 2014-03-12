
package cz.cvut.fel.adaptiverestfulapi.example.security;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.security.AuthenticationException;
import cz.cvut.fel.adaptiverestfulapi.security.basic.BasicAuthentication;
import cz.cvut.fel.adaptiverestfulapi.serialization.Authorization;

import java.util.*;


public class SimpleAuthorization implements Authorization {

    private Set<String> users;

    public SimpleAuthorization(List<String> users) {
        this.users = new HashSet<>(users);
    }

    @Override
    public boolean isAllowed(HttpContext httpContext) {
        try {
            String user = BasicAuthentication.user(httpContext, null).getKey();
            return this.users.contains(user);

        } catch (AuthenticationException e) {
            return false;
        }
    }

}
