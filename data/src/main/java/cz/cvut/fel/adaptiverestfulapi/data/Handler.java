
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


/**
 * Data handler.
 */
public interface Handler {

    /**
     * Handles HTTP context for entity.
     * @param entity The entity.
     * @param context The HTTP context.
     * @param configuration The configuration.
     * @return Processed HTTP context.
     */
    public HttpContext handle(Entity entity, HttpContext context, Configuration configuration);

}
