
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


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
     * Inspect property (attribute or relationship) from triplet of field, getter and setter methods.
     * @param field
     * @param getter
     * @param setter
     * @return property
     */
    public Property property(Field field, Method getter, Method setter);

}
