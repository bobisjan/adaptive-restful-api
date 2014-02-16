
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.InspectorListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;


public class Listener implements InspectorListener {

    @Override
    public Entity inspectEntity(Class clazz) {
        if (clazz.getSimpleName().equalsIgnoreCase("Person")) {
            return new Entity(clazz.getSimpleName(), clazz, new Configuration(new HashMap<String, Object>()));
        }
        return null;
    }

    @Override
    public Attribute inspectAttribute(Field field, Method getter, Method setter) {
        if (field != null) {
            // TODO implement
            return new Attribute(field.getName(), new Configuration(new HashMap<String, Object>()));

        }
        // TODO virtual getter, setter
        return null;
    }

    @Override
    public Relationship inspectRelationship(Field field, Method getter, Method setter) {
        if (field != null) {
            // TODO implement
            return new Relationship(field.getName(), new Configuration(new HashMap<String, Object>()));
        }
        // TODO virtual getter, setter
        return null;
    }
}
