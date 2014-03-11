
package cz.cvut.fel.adaptiverestfulapi.data.persistence;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.data.DataException;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;

import javax.persistence.EntityManager;


public class PostHandler extends cz.cvut.fel.adaptiverestfulapi.data.PostHandler {

    protected EntityManager manager;

    public PostHandler(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    protected HttpContext post(Entity entity, HttpContext context, Configuration configuration) throws DataException {
        Object object = context.getContent();

        if (object == null) {
            throw new DataException("Object for POST is null.");
        }

        Object result = this.create(entity, object);
        context.setContent(result);
        return context;
    }

    protected Object create(Entity entity, Object object) {
        this.manager.getTransaction().begin();
        this.manager.persist(object);
        this.manager.flush();
        this.manager.clear();
        this.manager.getTransaction().commit();
        return object;
    }

}
