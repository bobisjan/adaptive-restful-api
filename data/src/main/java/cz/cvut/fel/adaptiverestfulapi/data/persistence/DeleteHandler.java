
package cz.cvut.fel.adaptiverestfulapi.data.persistence;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaders;
import cz.cvut.fel.adaptiverestfulapi.core.HttpRouter;
import cz.cvut.fel.adaptiverestfulapi.core.HttpStatus;
import cz.cvut.fel.adaptiverestfulapi.data.DataException;
import cz.cvut.fel.adaptiverestfulapi.data.NotFoundException;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;

import javax.persistence.EntityManager;


public class DeleteHandler extends cz.cvut.fel.adaptiverestfulapi.data.DeleteHandler {

    protected EntityManager manager;

    public DeleteHandler(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    protected HttpContext delete(Entity entity, HttpContext context, Configuration configuration) throws DataException {
        Object identifier = context.getRouter().getIdentifier(entity.getPrimary().getAttributeType());

        if (identifier == null) {
            throw new DataException("Identifier for DELETE was null.");
        }

        Object object = this.manager.find(entity.getEntityClass(), identifier);
        if (object == null) {
            throw new NotFoundException(entity.getName(), identifier.toString());
        }

        this.delete(entity, object);

        context.response(HttpStatus.S_204, new HttpHeaders(), null);
        return context;
    }

    protected void delete(Entity entity, Object object) throws DataException {
        this.manager.getTransaction().begin();
        this.manager.remove(object);
        this.manager.getTransaction().commit();
    }

}
