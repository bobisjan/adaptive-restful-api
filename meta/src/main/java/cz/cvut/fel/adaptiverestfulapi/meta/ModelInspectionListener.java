
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Property;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;


/**
 * Listener for model inspection process.
 */
public interface ModelInspectionListener {

    /**
     * Inspect entity from class.
     * @param clazz
     * @return entity
     */
    public Entity entity(Class clazz);

    /**
     * Inspect property (attribute or relationship) from triplet of field, getter and setter methods,
     * entity and set of known entities.
     * @param triplet
     * @param entity
     * @param entities
     * @return property
     */
    public Property property(Triplet<Field, Method, Method> triplet, Entity entity, Set<Entity> entities);

}
