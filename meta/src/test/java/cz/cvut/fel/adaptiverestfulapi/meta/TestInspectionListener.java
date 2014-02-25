
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
    public Property property(Field field, Method getter, Method setter) {
        if (field != null) {
            if (field.getName().equalsIgnoreCase("description")
                    || field.getName().equalsIgnoreCase("name")) {
                return new Attribute(this.propertyName(field), getter, setter);

            } else if (field.getName().equalsIgnoreCase("project")) {
                return new Relationship(this.propertyName(field), getter, setter, field.getDeclaringClass().getPackage().getName() + ".Project");

            } else if (field.getName().equalsIgnoreCase("issues")) {
                return new Relationship(this.propertyName(field), getter, setter, field.getDeclaringClass().getPackage().getName() + ".Issue");
            }

            // TODO implement
            return new Attribute(field.getDeclaringClass().getName() + "." + field.getName(), getter, setter);

        }
        // TODO virtual getter, setter
        return null;
    }

    private String propertyName(Field field) {
        return field.getDeclaringClass().getName() + "." + field.getName();
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
