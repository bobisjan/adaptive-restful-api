
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


public interface GetHandler {

    public static final String Key = GetHandler.class.getName();

    public HttpContext get(Entity entity, HttpContext context, Configuration configuration);

}
