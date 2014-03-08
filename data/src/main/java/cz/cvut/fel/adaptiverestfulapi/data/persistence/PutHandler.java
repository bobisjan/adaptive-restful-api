
package cz.cvut.fel.adaptiverestfulapi.data.persistence;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.data.DataException;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class PutHandler extends cz.cvut.fel.adaptiverestfulapi.data.PutHandler {

    protected EntityManager manager;

    public PutHandler(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    protected HttpContext put(Entity entity, HttpContext context, Configuration configuration) throws DataException {
        String identifier = context.getRouter().getIdentifier();
        Object object = context.getContent();

        Object current = this.manager.find(entity.getEntityClass(), identifier);
        if (current == null) {
            // TODO 404
            throw new DataException("Entity " + entity + " with identifier " + identifier + " could not be found.");
        }

        Object result = this.update(entity, current, object);

        context.setContent(result);
        return context;
    }

    protected Object update(Entity entity, Object current, Object updated) throws DataException {
        this.manager.getTransaction().begin();
        this.merge(entity, current, updated);
        this.manager.merge(current);
        this.manager.getTransaction().commit();

        return current;
    }

    private void merge(Entity entity, Object current, Object updated) throws DataException {
        for (Attribute attribute : entity.getAttributes().values()) {
            try {
                // TODO skip primary key
                Method getter = attribute.getGetter();
                Method setter = attribute.getGetter();
                setter.invoke(current, getter.invoke(updated));

            } catch (IllegalAccessException e) {
                throw new DataException(e);

            } catch (InvocationTargetException e) {
                throw new DataException(e);
            }
        }

        // TODO merge relationships
    }

}
