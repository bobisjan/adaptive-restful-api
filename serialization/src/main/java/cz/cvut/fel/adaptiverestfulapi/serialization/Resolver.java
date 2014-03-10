
package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaders;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import java.util.List;


/**
 * Serialization resolver.
 */
public class Resolver extends Filter {

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        Serializer serializer = null;

        serializer = this.deserializer(httpContext, model, configuration);
        httpContext = serializer.deserialize(httpContext, model, configuration);

        httpContext = this.resign(httpContext, model, configuration);

        serializer = this.serializer(httpContext, model, configuration);
        return serializer.serialize(httpContext, model, configuration);
    }

    /**
     * Resolves most appropriate serializer for `Accept` header.
     * For now it's necessary to provide concrete type, like `application/json`.
     * @param httpContext
     * @param model
     * @param configuration
     * @return
     * @throws FilterException
     */
    protected Serializer serializer(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        List<String> values = httpContext.getRequestHeaders().get(HttpHeaders.Accept);
        Serializer serializer = this.resolve(values, model, configuration);
        if (serializer == null) {
            throw new FilterException("Could not resolve serializer.");
        }
        return serializer;
    }

    /**
     * Resolves most appropriate deserializer for `Content-Type` header.
     * For now it's necessary to provide concrete type, like `application/json`.
     * @param httpContext
     * @param model
     * @param configuration
     * @return
     * @throws FilterException
     */
    protected Serializer deserializer(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        List<String> values = httpContext.getRequestHeaders().get(HttpHeaders.ContentType);
        Serializer deserializer = this.resolve(values, model, configuration);

        if (deserializer == null) {
            throw new FilterException("Could not resolve deserializer.");
        }
        return deserializer;
    }

    private Serializer resolve(List<String> values, Model model, Configuration configuration) {
        Serializer serializer = null;

        for (String value : values) {
            serializer = configuration.get(value, model);
            if (serializer != null) {
                return serializer;
            }
        }
        return serializer;
    }

}
