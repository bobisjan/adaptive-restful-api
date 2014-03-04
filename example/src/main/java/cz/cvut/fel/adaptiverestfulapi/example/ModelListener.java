
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.meta.ModelInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Property;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;


/**
 * The default model inspection listener.
 */
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

    /**
     * Returns entity name from class.
     * @param clazz
     * @return The entity name.
     */
    protected String entityName(Class clazz) {
        return clazz.getName();
    }

    /**
     * Returns property name from triplet of field, getter and setter.
     * @param triplet
     * @param entity The entity of the property.
     * @return The property name.
     */
    protected String propertyName(Triplet<Field, Method, Method> triplet, Entity entity) {
        Field field = triplet.a;
        Method getter = triplet.b;
        Method setter = triplet.c;

        if (field != null) {
            return entity.getName() + "." + this.toFirstLetterLowerCase(field.getName());

        } else if (getter != null && getter.getName().startsWith("get"))  {
            return entity.getName() + "." + this.toFirstLetterLowerCase(getter.getName().substring("get".length()));

        } else if (getter != null && getter.getName().startsWith("is"))  {
            return entity.getName() + "." + this.toFirstLetterLowerCase(getter.getName().substring("is".length()));

        } else if (setter != null && setter.getName().startsWith("set"))  {
            return entity.getName() + "." + this.toFirstLetterLowerCase(setter.getName().substring("set".length()));

        } else {
            return null;
        }
    }

    /**
     * Looks for target entity from triplet of field, getter and setter.
     * @param triplet
     * @param entity The entity of the property.
     * @param entities The known entities in the model.
     * @return The target entity or null if not found.
     */
    protected Entity targetEntity(Triplet<Field, Method, Method> triplet, Entity entity, Set<Entity> entities) {
        Class<?> target = this.targetClass(triplet);

        for (Entity e : entities) {
            if (e.getEntityClass().equals(target)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Returns target class from triplet of field, getter and setter.
     * @param triplet
     * @return The target class.
     */
    protected Class<?> targetClass(Triplet<Field, Method, Method> triplet) {
        Class<?> target = null;

        if (triplet.a != null) {
            target = this.targetClassFromField(triplet.a);
            if (target == null) {
                target = this.targetClassFromAnnotations(triplet.a.getAnnotations());
            }

        } else if (triplet.b != null) {
            target = this.targetClassFromGetter(triplet.b);
            if (target == null) {
                target = this.targetClassFromAnnotations(triplet.b.getAnnotations());
            }

        } else if (triplet.c != null) {
            target = this.targetClassFromSetter(triplet.c);
            if (target == null) {
                target = this.targetClassFromAnnotations(triplet.c.getAnnotations());
            }
        }
        return target;
    }

    /**
     * Returns target class from field.
     * @param field
     * @return The target class.
     */
    protected Class<?> targetClassFromField(Field field) {
        Class<?> target = field.getType();

        if (Collection.class.isAssignableFrom(target)) {
            Type type = field.getGenericType();

            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType)type;
                Type[] arr = pType.getActualTypeArguments();
                target = (Class<?>)arr[0];
            }
        }
        return target;
    }

    /**
     * Returns target class from getter method.
     * @param getter
     * @return The target class.
     */
    protected Class<?> targetClassFromGetter(Method getter) {
        Class<?> target = getter.getReturnType();

        if (Collection.class.isAssignableFrom(target)) {
            Type type = getter.getGenericReturnType();

            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType)type;
                Type[] arr = pType.getActualTypeArguments();
                target = (Class<?>)arr[0];
            }
        }
        return target;
    }

    /**
     * Returns target class from setter method.
     * @param setter
     * @return The target class.
     */
    protected Class<?> targetClassFromSetter(Method setter) {
        Class<?> target = setter.getParameterTypes()[0];

        if (Collection.class.isAssignableFrom(target)) {
            Type type = setter.getGenericParameterTypes()[0];

            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType)type;
                Type[] arr = pType.getActualTypeArguments();
                target = (Class<?>)arr[0];
            }
        }
        return target;
    }

    /**
     * Looks for JPA relationship annotation to find target class.
     * @param annotations
     * @return The target class.
     */
    protected Class<?> targetClassFromAnnotations(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(OneToOne.class)) {
                OneToOne oneToOne = (OneToOne)annotation;
                return oneToOne.targetEntity();

            } else if (annotation.annotationType().equals(OneToMany.class)) {
                OneToMany oneToMany = (OneToMany)annotation;
                return oneToMany.targetEntity();

            }  else if (annotation.annotationType().equals(ManyToOne.class)) {
                ManyToOne manyToOne = (ManyToOne)annotation;
                return manyToOne.targetEntity();

            }  else if (annotation.annotationType().equals(ManyToMany.class)) {
                ManyToMany manyToMany = (ManyToMany)annotation;
                return manyToMany.targetEntity();
            }
        }
        return null;
    }

    /**
     * Makes first letter of the string lower cased.
     * @param string
     * @return The first letter lower cased string.
     */
    protected String toFirstLetterLowerCase(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

}
