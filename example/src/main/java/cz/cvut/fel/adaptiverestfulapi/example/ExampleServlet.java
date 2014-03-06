
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.data.Dispatcher;
import cz.cvut.fel.adaptiverestfulapi.servlet.FilteredServlet;


public class ExampleServlet extends FilteredServlet {

    public ExampleServlet() {
        this.filter = new Dispatcher();
    }

}
