
package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;


/**
 * JSON Serializer.
 */
public class JsonSerializer extends Serializer {

    @Override
    protected HttpContext serialize(HttpContext httpContext) throws SerializationException {
        return httpContext;
    }

    @Override
    protected HttpContext deserialize(HttpContext httpContext) throws SerializationException {
        return httpContext;
    }

}
