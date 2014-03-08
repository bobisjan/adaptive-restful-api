
package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


/**
 * Abstract serialization filter.
 */
public abstract class Serializer extends Filter {

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        httpContext = this.deserialize(httpContext, model, configuration);
        httpContext = this.resign(httpContext, model, configuration);
        return this.serialize(httpContext, model, configuration);
    }

    /**
     * Serializes response content in the HTTP context.
     * @param httpContext The HTTP context to serialize.
     * @param model The model.
     * @param configuration The configuration.
     * @return The serialized context.
     * @throws SerializationException
     */
    protected abstract HttpContext serialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException;

    /**
     * Deserializes request content in the HTTP context.
     * @param httpContext The HTTP context to deserialize.
     * @param model The model.
     * @param configuration The configuration.
     * @return The deserialized context.
     * @throws SerializationException
     */
    protected abstract HttpContext deserialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException;

}
