package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;


public abstract class Serializer extends Filter {

    @Override
    public HttpContext process(HttpContext httpContext) throws FilterException {
        return this.resign(httpContext);
    }

    protected abstract void serialize(HttpContext httpContext);

    protected abstract void deserialize(HttpContext httpContext);

}
