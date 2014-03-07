
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


public interface PostHandler {

    public static final String Key = PostHandler.class.getName();

    public HttpContext post(Entity entity, HttpContext context, Configuration configuration);

}
