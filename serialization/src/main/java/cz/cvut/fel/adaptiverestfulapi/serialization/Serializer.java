
package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


/**
 * Serialization interface.
 */
public interface Serializer {

    /**
     * Serializes response content in the HTTP context.
     * @param httpContext The HTTP context to serialize.
     * @param model The model.
     * @param configuration The configuration.
     * @return The serialized context.
     * @throws SerializationException
     */
    public HttpContext serialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException;

    /**
     * Deserializes request content in the HTTP context.
     * @param httpContext The HTTP context to deserialize.
     * @param model The model.
     * @param configuration The configuration.
     * @return The deserialized context.
     * @throws SerializationException
     */
    public HttpContext deserialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException;

}
