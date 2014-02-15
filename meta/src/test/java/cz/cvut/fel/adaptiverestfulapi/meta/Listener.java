
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.InspectorListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Listener implements InspectorListener {

    @Override
    public Entity inspect(Class clazz) {
        if (clazz.getSimpleName().equalsIgnoreCase("Person")) {
            return new Entity(clazz.getSimpleName(), clazz);
        }
        return null;
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
