
package cz.cvut.fel.adaptiverestfulapi.data.persistence;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.data.DataException;
import cz.cvut.fel.adaptiverestfulapi.data.NotFoundException;
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
        Attribute primary = entity.getPrimary();
        Object identifier = context.getRouter().getIdentifier(primary.getAttributeType());

        Object current = this.manager.find(entity.getEntityClass(), identifier);
        if (current == null) {
            throw new NotFoundException(entity.getName(), identifier.toString());
        }

        Object object = context.getContent();
        if (object == null) {
            throw new DataException("Object for POST is null.");
        }

        this.ensureIdentifier(entity, identifier, object);
        Object result = this.update(entity, identifier, object);

        context.setContent(result);
        return context;
    }

    protected Object update(Entity entity, Object identifier, Object object) throws DataException {
        this.manager.getTransaction().begin();
        this.manager.merge(object);
        this.manager.flush();
        this.manager.clear();
        this.manager.getTransaction().commit();
        return this.manager.find(entity.getEntityClass(), identifier);
    }

    private void ensureIdentifier(Entity entity, Object identifier, Object object) throws DataException {
        Attribute primary = entity.getPrimary();
        Method setter = primary.getSetter();

        try {
            setter.invoke(object, identifier);

        } catch (IllegalAccessException e) {
            throw new DataException(e);

        } catch (InvocationTargetException e) {
            throw new DataException(e);
        }
    }

}
