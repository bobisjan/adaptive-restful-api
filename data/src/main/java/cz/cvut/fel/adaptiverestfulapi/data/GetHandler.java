
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


public abstract class GetHandler implements Handler {

    public static final String Key = GetHandler.class.getName();

    protected abstract HttpContext get(Entity entity, HttpContext context, Configuration configuration);

    public final HttpContext handle(Entity entity, HttpContext context, Configuration configuration) {
        return this.get(entity, context, configuration);
    }

}
