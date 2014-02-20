
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Listener implements ModelInspection {

    @Override
    public Entity entity(Class clazz) {
        return new Entity(clazz.getName(), clazz);
    }

    @Override
    public Attribute attribute(Field field, Method getter, Method setter) {
        if (field != null) {
            // TODO implement
            return new Attribute(field.getDeclaringClass().getName() + "." + field.getName());

        }
        // TODO virtual getter, setter
        return null;
    }

    @Override
    public Relationship relationship(Field field, Method getter, Method setter) {
        if (field != null) {
            // TODO implement
            return new Relationship(field.getDeclaringClass().getName() + "." + field.getName());
        }
        // TODO virtual getter, setter
        return null;
    }
}
