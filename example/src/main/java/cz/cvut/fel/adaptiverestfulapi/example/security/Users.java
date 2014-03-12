
package cz.cvut.fel.adaptiverestfulapi.example.security;


import cz.cvut.fel.adaptiverestfulapi.core.HttpMethod;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;
import cz.cvut.fel.adaptiverestfulapi.security.Authentication;
import cz.cvut.fel.adaptiverestfulapi.security.Authorization;

import java.util.*;


public class Users {

    private static Users instance;

    private Map<String, String> passwords;
    private Map<String, Set<HttpMethod>> methods;

    private Authentication authentication;
    private Authorization methodAuthorization;

    public static Users getInstance() {
        if (instance == null) {
            instance = new Users();
        }
        return instance;
    }

    public Users() {
        this.passwords = new HashMap<>();
        this.passwords.put("admin", "1234");
        this.passwords.put("client", "4321");

        this.methods = new HashMap<>();
        this.methods.put("admin", new HashSet<HttpMethod>());
        this.methods.get("admin").add(HttpMethod.GET);
        this.methods.get("admin").add(HttpMethod.POST);
        this.methods.get("admin").add(HttpMethod.PUT);
        this.methods.get("admin").add(HttpMethod.DELETE);

        this.methods.put("client", new HashSet<HttpMethod>());
        this.methods.get("client").add(HttpMethod.GET);
    }

    public Authentication getAuthentication() {
        if (this.authentication == null) {
            this.authentication = new SimpleAuthentication(this.passwords, "example-realm");
        }
        return this.authentication;
    }

    public Authorization getMethodAuthorization() {
        if (this.methodAuthorization == null) {
            this.methodAuthorization = new MethodAuthorization(this.methods);
        }
        return this.methodAuthorization;
    }

    public cz.cvut.fel.adaptiverestfulapi.serialization.Authorization serializationAuthorization(Entity entity) {
        List<String> users = new LinkedList<>();
        users.add("admin");
        users.add("client");

        return new SimpleAuthorization(users);
    }

    public cz.cvut.fel.adaptiverestfulapi.serialization.Authorization serializationAuthorization(Attribute attribute) {
        List<String> users = new LinkedList<>();

        if (attribute.getShortName().equalsIgnoreCase("startedAt")) {
            users.add("admin");

        } else {
            users.add("client");
            users.add("admin");
        }

        return new SimpleAuthorization(users);
    }

    public cz.cvut.fel.adaptiverestfulapi.serialization.Authorization serializationAuthorization(Relationship relationship) {
        List<String> users = new LinkedList<>();
        users.add("admin");
        users.add("client");

        return new SimpleAuthorization(users);
    }

}
