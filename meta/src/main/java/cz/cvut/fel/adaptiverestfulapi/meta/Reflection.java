
package cz.cvut.fel.adaptiverestfulapi.meta;


import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Helper class for reflection.
 */
public class Reflection {

    /**
     * Creates reflections for package and base class.
     * @param pack package
     * @param clazz base class
     * @return reflections
     */
    public static Reflections reflections(String pack, Class clazz) {
        if (clazz.equals(Object.class)) {
            // @see http://stackoverflow.com/a/9571146
            List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
            classLoadersList.add(ClasspathHelper.contextClassLoader());
            classLoadersList.add(ClasspathHelper.staticClassLoader());

            return new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pack))));
        }
        return new Reflections(clazz);
    }

    /**
     * Finds all leaf classes of the base class in the reflections
     * @param reflections
     * @param clazz base class
     * @return set of leaf classes
     */
    public static Set<Class<?>> leafs(Reflections reflections, Class clazz) {
        Set<Class<?>> all = reflections.getSubTypesOf(clazz);
        Set<Class<?>> leafs = new HashSet<>();

        for (Class<?> c : all) {
            if (reflections.getSubTypesOf(c.getClass()).isEmpty()) {
                leafs.add(c);
            }
        }
        return leafs;
    }

    /**
     * Returns all fields in the class.
     * @param clazz
     * @return set of fields
     */
    public static Set<Field> fields(Class clazz) {
        Set<Field> fields = new HashSet<>();

        fields.addAll(ReflectionUtils.getAllFields(clazz));
        return fields;
    }

    /**
     * Returns getter method for the field
     * @param field
     * @param getters
     * @return getter
     */
    public static Method getter(Field field, Set<Method> getters) {
        return getter(field.getName(), getters);
    }

    /**
     * Returns getter for the corresponding setter method.
     * @param setter
     * @param getters
     * @return getter
     */
    public static Method getter(Method setter, Set<Method> getters) {
        return getter(setter.getName().substring(3), getters);
    }

    /**
     * Returns getter for the name.
     * @param name
     * @param getters
     * @return getter
     */
    public static Method getter(String name, Set<Method> getters) {
        for (Method getter : getters) {
            if (getter.getName().equalsIgnoreCase("is" + name.toUpperCase())
                    || getter.getName().equalsIgnoreCase("get" + name.toUpperCase())) {
                return getter;
            }
        }
        return null;
    }

    /**
     * Returns setter for the field.
     * @param field
     * @param setters
     * @return setter
     */
    public static Method setter(Field field, Set<Method> setters) {
        for (Method setter : setters) {
            if (setter.getName().equalsIgnoreCase("set" + field.getName().toUpperCase())) {
                return setter;
            }
        }
        return null;
    }

    /**
     * Returns getters for the class. Getter is public method which starts with `get` or `is` and takes no parameters.
     * @param clazz
     * @return set of getters
     */
    public static Set<Method> getters(Class clazz) {
        Set<Method> getters = new HashSet<>();

        getters.addAll(ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withModifier(Modifier.PUBLIC),
                ReflectionUtils.withPrefix("get"),
                ReflectionUtils.withParametersCount(0)));
        getters.addAll(ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withModifier(Modifier.PUBLIC),
                ReflectionUtils.withPrefix("is"),
                ReflectionUtils.withParametersCount(0)));
        return getters;
    }

    /**
     * Returns setters for the class. Setter is public method which starts with `set` and takes only one parameter.
     * @param clazz
     * @return set of setters
     */
    public static Set<Method> setters(Class clazz) {
        Set<Method> setters = new HashSet<>();

        setters.addAll(ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withModifier(Modifier.PUBLIC),
                ReflectionUtils.withPrefix("set"),
                ReflectionUtils.withParametersCount(1)));
        return setters;
    }

}
