
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.data.Dispatcher;
import cz.cvut.fel.adaptiverestfulapi.serialization.Resolver;
import cz.cvut.fel.adaptiverestfulapi.servlet.FilteredServlet;


public class ExampleServlet extends FilteredServlet {

    public ExampleServlet() {
        Filter auth = new ExampleAuthentication();
        Filter serializer = new Resolver();
        Filter data = new Dispatcher();

        auth.setNext(serializer.setNext(data));
        this.filter = auth;
    }

}
