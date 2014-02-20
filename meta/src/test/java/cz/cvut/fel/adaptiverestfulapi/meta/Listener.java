
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Listener implements ModelInspection {

    @Override
    public Entity inspectEntity(Class clazz) {
        return new Entity(clazz.getSimpleName(), clazz);
    }

    @Override
    public Attribute inspectAttribute(Field field, Method getter, Method setter) {
        if (field != null) {
            // TODO implement
            return new Attribute(field.getName());

        }
        // TODO virtual getter, setter
        return null;
    }

    @Override
    public Relationship inspectRelationship(Field field, Method getter, Method setter) {
        if (field != null) {
            // TODO implement
            return new Relationship(field.getName());
        }
        // TODO virtual getter, setter
        return null;
    }
}
