
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


public interface DeleteHandler {

    public static final String Key = DeleteHandler.class.getName();

    public HttpContext delete(Entity entity, HttpContext context, Configuration configuration);

}
