
package cz.cvut.fel.adaptiverestfulapi.example.security;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.HttpMethod;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.security.AuthenticationException;
import cz.cvut.fel.adaptiverestfulapi.security.Authorization;
import cz.cvut.fel.adaptiverestfulapi.security.AuthorizationException;
import cz.cvut.fel.adaptiverestfulapi.security.basic.BasicAuthentication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MethodAuthorization extends Authorization {

    private Map<String, Set<HttpMethod>> methods;

    public MethodAuthorization(Map<String, Set<HttpMethod>> methods) {
        this.methods = new HashMap<>();
        for (Map.Entry<String, Set<HttpMethod>> entry : methods.entrySet()) {
            Set<HttpMethod> set = new HashSet<>();
            for (HttpMethod m : entry.getValue()) {
                set.add(m);
            }
            this.methods.put(entry.getKey(), set);
        }
    }

    public void allow(String user, HttpMethod method) {
        if (!this.methods.containsKey(user)) {
            this.methods.put(user, new HashSet<HttpMethod>());
        }
        this.methods.get(user).add(method);
    }

    public void deny(String user, HttpMethod method) {
        if (this.methods.containsKey(user)) {
            this.methods.get(user).remove(method);
        }
    }

    @Override
    protected void authorize(HttpContext httpContext, Model model, Configuration configuration) throws AuthorizationException {
        try {
            String user = BasicAuthentication.user(httpContext, null).getKey();
            HttpMethod method = httpContext.getMethod();

            if (this.methods.containsKey(user) && this.methods.get(user).contains(method)) {
                return;
            }
            throw new AuthorizationException();

        } catch (AuthenticationException e) {
            throw new AuthorizationException(e);
        }
    }

}
