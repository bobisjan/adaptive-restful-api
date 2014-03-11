
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Variable;
import cz.cvut.fel.adaptiverestfulapi.meta.model.*;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
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
            if (field.getName().equalsIgnoreCase("description")) {
                return new Attribute(this.propertyName(field), getter, setter, String.class);

            } else if (field.getName().equalsIgnoreCase("name")) {
                return new Attribute(this.propertyName(field), getter, setter, String.class);

            } else if (field.getName().equalsIgnoreCase("startedAt")) {
                return new Attribute(this.propertyName(field), getter, setter, Date.class);

            } else if (field.getName().equalsIgnoreCase("project")) {
                return new Relationship(this.propertyName(field), getter, setter, field.getDeclaringClass().getPackage().getName() + ".Project", RelationshipType.ToOne);

            } else if (field.getName().equalsIgnoreCase("issues")) {
                return new Relationship(this.propertyName(field), getter, setter, field.getDeclaringClass().getPackage().getName() + ".Issue", RelationshipType.ToMany);
            }
        }

        if (getter != null && setter != null) {
            if (getter.getName().equalsIgnoreCase("isStarted") && setter.getName().equalsIgnoreCase("setStarted")) {
                return new Attribute(this.propertyName(getter, "started"), getter, setter, Boolean.class);
            }
            return null;

        } else if (getter != null) {
            if (getter.getName().equalsIgnoreCase("getLocalizedDescription")) {
                return new Attribute(this.propertyName(getter, "localizedDescription"), getter, null, String.class);
            }
            return null;

        } else if (setter != null) {
            if (setter.getName().equalsIgnoreCase("setLowerCasedName")) {
                return new Attribute(this.propertyName(setter, "lowerCasedName"), null, setter, String.class);
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
