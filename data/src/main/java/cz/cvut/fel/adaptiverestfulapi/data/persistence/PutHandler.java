
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

        this.compareIdentifiers(entity, current, object);
        Object result = this.update(entity, current, object);

        context.setContent(result);
        return context;
    }

    protected Object update(Entity entity, Object current, Object updated) throws DataException {
        this.manager.getTransaction().begin();
        Object merged = this.manager.merge(updated);
        this.manager.flush();
        this.manager.clear();
        this.manager.getTransaction().commit();
        return current;
    }

    private void compareIdentifiers(Entity entity, Object current, Object updated) throws DataException {
        Attribute primary = entity.getPrimary();
        Method getter = primary.getGetter();

        try {
            Object currentIdentifier = getter.invoke(current);
            Object updatedIdentifier = getter.invoke(updated);

            if (updatedIdentifier == null) {
                return;
            }

            if (!updatedIdentifier.equals(currentIdentifier)) {
                throw new DataException("PUT: Identifier provided in the JSON is not equal with identifier in the URL.");
            }

        } catch (IllegalAccessException e) {
            throw new DataException(e);

        } catch (InvocationTargetException e) {
            throw new DataException(e);
        }

    }

}
