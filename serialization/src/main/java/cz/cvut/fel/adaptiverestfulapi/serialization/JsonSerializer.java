
package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


/**
 * JSON serializer.
 */
public class JsonSerializer implements Serializer {

    @Override
    public HttpContext serialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException {
        // TODO implement JSON serialization
        return httpContext;
    }

    @Override
    public HttpContext deserialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException {
        // TODO implement JSON deserialization
        return httpContext;
    }

}
