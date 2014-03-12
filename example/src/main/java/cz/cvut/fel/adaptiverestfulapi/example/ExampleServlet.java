
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.caching.IfModifiedSinceCache;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.data.Dispatcher;
import cz.cvut.fel.adaptiverestfulapi.example.security.SimpleAuthentication;
import cz.cvut.fel.adaptiverestfulapi.example.security.Users;
import cz.cvut.fel.adaptiverestfulapi.serialization.Resolver;
import cz.cvut.fel.adaptiverestfulapi.servlet.FilteredServlet;


public class ExampleServlet extends FilteredServlet {

    public ExampleServlet() {
        Filter authentication = Users.getInstance().getAuthentication();
        Filter authorization = Users.getInstance().getMethodAuthorization();
        Filter cache = new IfModifiedSinceCache();
        Filter serializer = new Resolver();
        Filter data = new Dispatcher();

        authentication.setNext(authorization.setNext(cache.setNext(serializer.setNext(data))));
        this.filter = authentication;
    }

}
