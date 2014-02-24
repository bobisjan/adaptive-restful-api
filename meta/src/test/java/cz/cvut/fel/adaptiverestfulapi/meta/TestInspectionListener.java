
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Variable;
import cz.cvut.fel.adaptiverestfulapi.meta.model.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;


public class TestInspectionListener implements ModelInspectionListener, ConfigurationInspectionListener {

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

    @Override
    public List<Variable> configuration() {
        return new LinkedList<>();
    }

    @Override
    public List<Variable> configuration(Model model) {
        return new LinkedList<>();
    }

    @Override
    public List<Variable> configuration(Entity entity) {
        return new LinkedList<>();
    }

    @Override
    public List<Variable> configuration(Attribute attribute) {
        return new LinkedList<>();
    }

    @Override
    public List<Variable> configuration(Relationship relationship) {
        return new LinkedList<>();
    }

}
