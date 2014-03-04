
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.meta.ModelInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Property;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;


public class ModelListener implements ModelInspectionListener {

    @Override
    public Entity entity(Class clazz) {
        return new Entity(this.entityName(clazz), clazz);
    }

    @Override
    public Property property(Triplet<Field, Method, Method> triplet, Entity entity, Set<Entity> entities) {
        // TODO make property inspector generic and then move it to the core

        Field field = triplet.a;
        Method getter = triplet.b;
        Method setter = triplet.c;

        if (field != null) {
            if (field.getName().equalsIgnoreCase("project")) {
                return new Relationship(this.propertyName(field), getter, setter, field.getDeclaringClass().getPackage().getName() + ".Project");

            } else if (field.getName().equalsIgnoreCase("issues")) {
                return new Relationship(this.propertyName(field), getter, setter, field.getDeclaringClass().getPackage().getName() + ".Issue");

            } else {
                return new Attribute(this.propertyName(field), getter, setter);
            }
        }

        if (getter != null && setter != null) {
            if (getter.getName().equalsIgnoreCase("isStarted") && setter.getName().equalsIgnoreCase("setStarted")) {
                return new Attribute(this.propertyName(getter, "started"), getter, setter);
            }
            return null;

        } else if (getter != null) {
            if (getter.getName().equalsIgnoreCase("getLocalizedDescription")) {
                return new Attribute(this.propertyName(getter, "localizedDescription"), getter, null);
            }
            return null;

        } else if (setter != null) {
            if (setter.getName().equalsIgnoreCase("setLowerCasedName")) {
                return new Attribute(this.propertyName(setter, "lowerCasedName"), null, setter);
            }
            return null;

        } else {
            return null;
        }
    }

    protected String entityName(Class clazz) {
        return clazz.getName();
    }

    private String propertyName(Field field) {
        return field.getDeclaringClass().getName() + "." + field.getName();
    }

    private String propertyName(Method method, String name) {
        return method.getDeclaringClass().getName() + "." + name;
    }

}
