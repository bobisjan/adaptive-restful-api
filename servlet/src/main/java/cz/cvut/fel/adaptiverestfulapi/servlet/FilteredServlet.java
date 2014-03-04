
package cz.cvut.fel.adaptiverestfulapi.servlet;

import cz.cvut.fel.adaptiverestfulapi.core.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FilteredServlet extends HttpServlet {

    protected ServletFilter filter;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext applicationContext = ApplicationContext.getInstance();

        try {
            this.filter.process(null, applicationContext.getModel(), applicationContext.getConfiguration());

        } catch (FilterException e) {
            throw new ServletException(e);
        }
    }

}
