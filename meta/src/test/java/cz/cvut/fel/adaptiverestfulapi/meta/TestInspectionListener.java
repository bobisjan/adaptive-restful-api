
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Variable;
import cz.cvut.fel.adaptiverestfulapi.meta.model.*;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class TestInspectionListener implements ModelInspectionListener, ConfigurationInspectionListener {

    @Override
    public Entity entity(Class clazz) {
        return new Entity(clazz.getName(), clazz);
    }

    @Override
    public Property property(Triplet<Field, Method, Method> triplet, Entity entity, Set<Entity> entities) {
        Field field = triplet.a;
        Method getter = triplet.b;
        Method setter = triplet.c;

        if (field != null) {
            if (field.getName().equalsIgnoreCase("description")
                    || field.getName().equalsIgnoreCase("name")
                    || field.getName().equalsIgnoreCase("startedAt")) {
                return new Attribute(this.propertyName(field), getter, setter);

            } else if (field.getName().equalsIgnoreCase("project")) {
                return new Relationship(this.propertyName(field), getter, setter, field.getDeclaringClass().getPackage().getName() + ".Project");

            } else if (field.getName().equalsIgnoreCase("issues")) {
                return new Relationship(this.propertyName(field), getter, setter, field.getDeclaringClass().getPackage().getName() + ".Issue");
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

    private String propertyName(Field field) {
        return field.getDeclaringClass().getName() + "." + field.getName();
    }

    private String propertyName(Method method, String name) {
        return method.getDeclaringClass().getName() + "." + name;
    }

    @Override
    public List<Variable> configuration() {
        List<Variable> variables = new LinkedList<>();
        variables.add(new Variable("Serialization", "JSON"));
        return variables;
    }

    @Override
    public List<Variable> configuration(Model model) {
        List<Variable> variables = new LinkedList<>();

        variables.add(new Variable("ABC", "a"));
        return variables;
    }

    @Override
    public List<Variable> configuration(Entity entity) {
        List<Variable> variables = new LinkedList<>();

        return variables;
    }

    @Override
    public List<Variable> configuration(Attribute attribute) {
        List<Variable> variables = new LinkedList<>();

        if (attribute.getName().endsWith("description")) {
            variables.add(new Variable("ABC", "z"));
        }
        return variables;
    }

    @Override
    public List<Variable> configuration(Relationship relationship) {
        List<Variable> variables = new LinkedList<>();

        return variables;
    }

}
