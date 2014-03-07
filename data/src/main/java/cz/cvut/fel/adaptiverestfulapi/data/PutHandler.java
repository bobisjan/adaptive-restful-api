
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


public interface PutHandler {

    public static final String Key = PutHandler.class.getName();

    public HttpContext put(Entity entity, HttpContext context, Configuration configuration);

}
