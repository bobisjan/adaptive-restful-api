
package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


/**
 * Serialization resolver.
 */
public class Resolver extends Filter {

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        Serializer serializer = this.serializer(httpContext, model, configuration);
        httpContext = serializer.deserialize(httpContext, model, configuration);
        httpContext = this.resign(httpContext, model, configuration);
        return serializer.serialize(httpContext, model, configuration);
    }

    protected Serializer serializer(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        // TODO resolve serializer
        throw new FilterException("Could not resolve serializer.");
    }

}
