
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


/**
 * Abstract handler for PUT method.
 */
public abstract class PutHandler implements Handler {

    public static final String Key = PutHandler.class.getName();

    /**
     * Handles PUT method.
     * @param entity The entity.
     * @param context The HTTP context.
     * @param configuration The configuration.
     * @return Processed HTTP context.
     */
    protected abstract HttpContext put(Entity entity, HttpContext context, Configuration configuration);

    @Override
    public final HttpContext handle(Entity entity, HttpContext context, Configuration configuration) {
        return this.put(entity, context, configuration);
    }

}
