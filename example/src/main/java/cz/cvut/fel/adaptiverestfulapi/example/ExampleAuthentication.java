
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.security.BasicAuthentication;

import java.util.HashMap;
import java.util.Map;


public class ExampleAuthentication extends BasicAuthentication {

    private Map<String, String> users;

    public ExampleAuthentication() {
        super("example-realm");

        this.users = new HashMap<String, String>();
        users.put("admin", "1234");
    }

    @Override
    protected boolean isAuthenticated(String username, String password) {
        return this.users.containsKey(username) && this.users.get(username).equals(password);
    }
}
