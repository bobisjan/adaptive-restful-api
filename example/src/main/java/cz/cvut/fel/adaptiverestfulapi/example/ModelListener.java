
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.meta.ModelInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Property;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;


public class ModelListener implements ModelInspectionListener {

    @Override
    public Entity entity(Class clazz) {
        return new Entity(this.entityName(clazz), clazz);
    }

    @Override
    public Property property(Triplet<Field, Method, Method> triplet, Entity entity, Set<Entity> entities) {
        Method getter = triplet.b;
        Method setter = triplet.c;

        String name = this.propertyName(triplet, entity);
        Entity targetEntity = this.targetEntity(triplet, entity, entities);

        if (targetEntity != null) {
            return new Relationship(name, getter, setter, targetEntity.getName());
        }
        return new Attribute(name, getter, setter);
    }

    protected String entityName(Class clazz) {
        return clazz.getName();
    }

    protected String propertyName(Triplet<Field, Method, Method> triplet, Entity entity) {
        Field field = triplet.a;
        Method getter = triplet.b;
        Method setter = triplet.c;

        if (field != null) {
            return entity.getName() + "." + field.getName();

        } else if (getter != null && getter.getName().startsWith("get"))  {
            return entity.getName() + "." + getter.getName().substring("get".length());

        } else if (getter != null && getter.getName().startsWith("is"))  {
            return entity.getName() + "." + getter.getName().substring("is".length());

        } else if (setter != null && setter.getName().startsWith("set"))  {
            return entity.getName() + "." + setter.getName().substring("set".length());

        } else {
            return null;
        }
    }

    protected Entity targetEntity(Triplet<Field, Method, Method> triplet, Entity entity, Set<Entity> entities) {
        Class<?> target = this.targetClass(triplet);

        for (Entity e : entities) {
            if (e.getEntityClass().equals(target)) {
                return e;
            }
        }
        return null;
    }

    // TODO Add check for JPA relationship annotation
    // TODO Refactor
    protected Class<?> targetClass(Triplet<Field, Method, Method> triplet) {
        Class<?> target = null;

        if (triplet.a != null) {
            target = triplet.a.getType();

            if (Collection.class.isAssignableFrom(target)) {
                Type type = triplet.a.getGenericType();

                if (type instanceof ParameterizedType) {
                    ParameterizedType pType = (ParameterizedType)type;
                    Type[] arr = pType.getActualTypeArguments();
                    target = (Class<?>)arr[0];
                }
            }

        } else if (triplet.b != null) {
            target = triplet.b.getReturnType();

            if (Collection.class.isAssignableFrom(target)) {
                Type type = triplet.b.getGenericReturnType();

                if (type instanceof ParameterizedType) {
                    ParameterizedType pType = (ParameterizedType)type;
                    Type[] arr = pType.getActualTypeArguments();
                    target = (Class<?>)arr[0];
                }
            }

        } else if (triplet.c != null) {
            target = triplet.c.getParameterTypes()[0];

            if (Collection.class.isAssignableFrom(target)) {
                Type type = triplet.c.getGenericParameterTypes()[0];

                if (type instanceof ParameterizedType) {
                    ParameterizedType pType = (ParameterizedType)type;
                    Type[] arr = pType.getActualTypeArguments();
                    target = (Class<?>)arr[0];
                }
            }
        }
        return target;
    }

}
