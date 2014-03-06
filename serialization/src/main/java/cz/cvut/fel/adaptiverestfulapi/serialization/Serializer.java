
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
        httpContext = this.deserialize(httpContext);
        httpContext = this.resign(httpContext, model, configuration);
        return this.serialize(httpContext);
    }

    /**
     * Serializes response content in the HTTP context.
     * @param httpContext The HTTP context to serialize.
     * @return The serialized context.
     * @throws SerializationException
     */
    protected abstract HttpContext serialize(HttpContext httpContext) throws SerializationException;

    /**
     * Deserializes request content in the HTTP context.
     * @param httpContext The HTTP context to deserialize.
     * @return The deserialized context.
     * @throws SerializationException
     */
    protected abstract HttpContext deserialize(HttpContext httpContext) throws SerializationException;

}
