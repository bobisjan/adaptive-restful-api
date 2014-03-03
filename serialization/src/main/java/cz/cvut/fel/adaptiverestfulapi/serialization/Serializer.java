package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


public abstract class Serializer extends Filter {

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        return this.resign(httpContext, model, configuration);
    }

    protected abstract void serialize(HttpContext httpContext);

    protected abstract void deserialize(HttpContext httpContext);

}
