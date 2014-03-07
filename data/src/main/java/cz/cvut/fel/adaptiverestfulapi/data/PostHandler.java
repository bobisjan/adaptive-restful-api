
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


/**
 * Abstract handler for POST method.
 */
public abstract class PostHandler implements Handler {

    public static final String Key = PostHandler.class.getName();

    /**
     * Handles POST method.
     * @param entity The entity.
     * @param context The HTTP context.
     * @param configuration The configuration.
     * @return Processed HTTP context.
     */
    protected abstract HttpContext post(Entity entity, HttpContext context, Configuration configuration);

    @Override
    public final HttpContext handle(Entity entity, HttpContext context, Configuration configuration) {
        return this.post(entity, context, configuration);
    }

}
