
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


/**
 * Abstract handler for DELETE method.
 */
public abstract class DeleteHandler implements Handler {

    public static final String Key = DeleteHandler.class.getName();

    /**
     * Handles DELETE method.
     * @param entity The entity.
     * @param context The HTTP context.
     * @param configuration The configuration.
     * @return Processed HTTP context.
     * @throws DataException
     */
    protected abstract HttpContext delete(Entity entity, HttpContext context, Configuration configuration) throws DataException;

    @Override
    public final HttpContext handle(Entity entity, HttpContext context, Configuration configuration) throws DataException {
        return this.delete(entity, context, configuration);
    }

}
