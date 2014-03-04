
package cz.fel.cvut.adaptiverestfulapi.servlet;

import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ResponseFilter extends Filter {

    private HttpServletResponse response;

    public ResponseFilter(HttpServletResponse response) {
        this(response, null);
    }

    public ResponseFilter(HttpServletResponse response, Filter next) {
        super(next);
        this.response = response;
    }

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        this.response.setStatus(httpContext.getStatus().getCode());

        try {
            this.response.getWriter().write(httpContext.getResponseContent());
            this.response.getWriter().flush();
            this.response.getWriter().close();

        } catch (IOException e) {
            throw new FilterException(e);
        }
        return httpContext;
    }

}
