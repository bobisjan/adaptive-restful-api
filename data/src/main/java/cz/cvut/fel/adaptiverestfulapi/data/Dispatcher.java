package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;


public abstract class Dispatcher extends Filter {

    @Override
    public HttpContext process(HttpContext httpContext) throws FilterException {
        return this.resign(httpContext);
    }

}
