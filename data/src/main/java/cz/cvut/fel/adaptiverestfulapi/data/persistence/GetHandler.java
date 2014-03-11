
package cz.cvut.fel.adaptiverestfulapi.data.persistence;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.HttpRouter;
import cz.cvut.fel.adaptiverestfulapi.data.DataException;
import cz.cvut.fel.adaptiverestfulapi.data.NotFoundException;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class GetHandler extends cz.cvut.fel.adaptiverestfulapi.data.GetHandler {

    protected EntityManager manager;

    public GetHandler(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    protected HttpContext get(Entity entity, HttpContext context, Configuration configuration) throws DataException {
        HttpRouter router = context.getRouter();
        Object identifier = router.getIdentifier(entity.getPrimary().getAttributeType());
        Object result = null;

        if (identifier != null) {
            result = this.find(entity, identifier);
            if (result == null) {
                throw new NotFoundException(entity.getName(), identifier.toString());
            }

        } else {
            result = this.findAll(entity);
        }

        context.setContent(result);
        return context;
    }

    protected Object find(Entity entity, Object identifier) {
        return this.manager.find(entity.getEntityClass(), identifier);
    }

    protected List findAll(Entity entity) {
        CriteriaBuilder qb = this.manager.getCriteriaBuilder();
        CriteriaQuery criteria = qb.createQuery(entity.getEntityClass());
        Root root = criteria.from(entity.getEntityClass());
        TypedQuery query = this.manager.createQuery(criteria);
        return query.getResultList();
    }

}
