
package cz.cvut.fel.adaptiverestfulapi.servlet;

import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletFilter extends Filter {

    public ServletFilter(HttpServletRequest request, HttpServletResponse response, Filter inner) {
        super(new RequestFilter(request, inner.setLast(new ResponseFilter(response))));
    }

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        return this.resign(httpContext, model, configuration);
    }

}
