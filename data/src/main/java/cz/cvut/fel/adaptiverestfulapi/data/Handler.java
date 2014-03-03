
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;


public interface Handler {

    public Object GET(Entity entity, HttpContext context, Configuration configuration);

    public Object POST(Entity entity, HttpContext context, Configuration configuration);

    public Object PUT(Entity entity, HttpContext context, Configuration configuration);

    public Object DELETE(Entity entity, HttpContext context, Configuration configuration);

}
