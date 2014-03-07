
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.data.Dispatcher;
import cz.cvut.fel.adaptiverestfulapi.servlet.FilteredServlet;

import java.util.HashMap;
import java.util.Map;


public class ExampleServlet extends FilteredServlet {

    public ExampleServlet() {
        Filter auth = new ExampleAuthentication();
        Filter data = new Dispatcher();

        auth.setNext(data);

        this.filter = auth;
    }

}
