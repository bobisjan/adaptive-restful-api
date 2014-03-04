
package cz.fel.cvut.adaptiverestfulapi.servlet;

import cz.cvut.fel.adaptiverestfulapi.core.*;
import cz.cvut.fel.adaptiverestfulapi.data.Dispatcher;
import cz.cvut.fel.adaptiverestfulapi.meta.InspectionException;
import cz.cvut.fel.adaptiverestfulapi.meta.Inspector;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;


public class FilteredServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext applicationContext = ApplicationContext.getInstance();

        Filter filter = new ExampleFilter(req, resp);

        try {
            filter.process(null, applicationContext.getModel(), applicationContext.getConfiguration());

        } catch (FilterException e) {
            throw new ServletException(e);
        }
    }

}
