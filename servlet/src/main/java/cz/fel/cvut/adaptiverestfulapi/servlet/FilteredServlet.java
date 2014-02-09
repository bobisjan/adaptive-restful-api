package cz.fel.cvut.adaptiverestfulapi.servlet;

import cz.cvut.fel.adaptiverestfulapi.core.Context;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.fel.cvut.adaptiverestfulapi.servlet.utils.RequestReader;
import cz.fel.cvut.adaptiverestfulapi.servlet.utils.ResponseWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *
 */
public class FilteredServlet extends HttpServlet {

    protected Filter chain;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this.chain == null) {
            throw new FilteredServletException("The filter's chain can not be null.");
        }

        Context context = RequestReader.context(req);

        try {
            this.chain.process(context);

        } catch (FilterException e) {
            // TODO set context to 500 Server Error
            e.printStackTrace();

        } finally {
            ResponseWriter.write(context, resp);
        }
    }

}


