
package cz.cvut.fel.adaptiverestfulapi.example.security;

import cz.cvut.fel.adaptiverestfulapi.security.basic.BasicAuthentication;

import java.util.HashMap;
import java.util.Map;


public class SimpleAuthentication extends BasicAuthentication {

    private Map<String, String> users;

    public SimpleAuthentication(Map<String, String> users, String realm) {
        super(realm);

        this.users = new HashMap<String, String>();
        for (Map.Entry<String, String> user : users.entrySet()) {
            this.users.put(user.getKey(), user.getValue());
        }
    }

    @Override
    protected boolean isAuthenticated(String username, String password) {
        return this.users.containsKey(username) && this.users.get(username).equals(password);
    }
}
