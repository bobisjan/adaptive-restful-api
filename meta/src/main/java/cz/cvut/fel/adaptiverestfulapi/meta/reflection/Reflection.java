
package cz.cvut.fel.adaptiverestfulapi.meta.reflection;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.*;
import java.util.*;


/**
 * Helper class for reflection.
 *
 * Getter is a public method which starts with `get` or `is` followed by name and takes no parameters.
 * Setter is a public method which starts with `set` followed by name and takes only one parameter.
 */
public class Reflection {

    private final Reflections reflections;

    /**
     * Creates reflection for package and base class.
     * @param pack
     * @param clazz
     */
    public Reflection(String pack, Class clazz) {
        if (clazz.equals(Object.class)) {
            // @see http://stackoverflow.com/a/9571146
            List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
            classLoadersList.add(ClasspathHelper.contextClassLoader());
            classLoadersList.add(ClasspathHelper.staticClassLoader());

            this.reflections = new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pack))));

        } else {
            this.reflections = new Reflections(clazz);
        }
    }


    /**
     * Finds all leaf classes of the base class.
     * @param clazz base class
     * @return set of leaf classes
     */
    public Set<Class<?>> leafs(Class clazz) {
        Set<Class<?>> all = this.reflections.getSubTypesOf(clazz);
        Set<Class<?>> leafs = new HashSet<>();

        for (Class<?> c : all) {
            if (this.reflections.getSubTypesOf(c).isEmpty()) {
                leafs.add(c);
            }
        }
        return leafs;
    }

    /**
     * Looks up for triplets of field, related getter and setter in the class.
     * Triplet can contain combinations of
     * - field, getter and/or setter,
     * - getter and/or setter.
     * @param clazz
     * @return set of triplets
     */
    public Set<Triplet<Field, Method, Method>> triplets(Class clazz) {
        Set<Triplet<Field, Method, Method>> triplets = new HashSet<>();

        Set<Field> fields = Reflection.fields(clazz);
        Set<Method> getters = Reflection.getters(clazz);
        Set<Method> setters = Reflection.setters(clazz);

        Method getter, setter;

        // step 1: add fields with getters, and setters (matched by field name)
        for (Field field : fields) {
            getter = Reflection.getter(field, getters);
            setter = Reflection.setter(field, setters);

            if (getter == null && setter == null) {
                continue;
            }
            if (getter != null) {
                getters.remove(getter);
            }
            if (setter != null) {
                setters.remove(setter);
            }
            triplets.add(new Triplet<Field, Method, Method>(field, getter, setter));
        }

        // step 2a: add remaining getter and setter pairs (matched by method name)
        // step 2b: add remaining setters
        for (Method s : setters) {
            getter = Reflection.getter(s, getters);

            if (getter != null) {
                triplets.add(new Triplet<Field, Method, Method>(null, getter, s));
                getters.remove(getter);

            } else {
                triplets.add(new Triplet<Field, Method, Method>(null, null, s));
            }
        }

        // step 3: add remaining getters
        for (Method g : getters) {
            triplets.add(new Triplet<Field, Method, Method>(null, g, null));
        }

        return triplets;
    }

    /**
     * Returns all fields in the class.
     * @param clazz
     * @return set of fields
     */
    private static Set<Field> fields(Class clazz) {
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
    private static Method getter(Field field, Set<Method> getters) {
        return getter(field.getName(), getters);
    }

    /**
     * Returns getter for the corresponding setter method.
     * @param setter
     * @param getters
     * @return getter
     */
    private static Method getter(Method setter, Set<Method> getters) {
        return getter(setter.getName().substring(3), getters);
    }

    /**
     * Returns getter for the name.
     * @param name
     * @param getters
     * @return getter
     */
    private static Method getter(String name, Set<Method> getters) {
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
    private static Method setter(Field field, Set<Method> setters) {
        for (Method setter : setters) {
            if (setter.getName().equalsIgnoreCase("set" + field.getName().toUpperCase())) {
                return setter;
            }
        }
        return null;
    }

    /**
     * Returns getters for the class.
     * @param clazz
     * @return set of getters
     */
    private static Set<Method> getters(Class clazz) {
        Set<Method> getters = new HashSet<>();

        Set<Method> methods = null;

        methods = ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withModifier(Modifier.PUBLIC),
                ReflectionUtils.withPrefix("get"),
                ReflectionUtils.withParametersCount(0));

        for (Method method : methods) {
            if (Modifier.isAbstract(method.getModifiers())
                    || Modifier.isInterface(method.getModifiers())) {
                continue;
            }
            getters.add(method);
        }

        methods = ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withModifier(Modifier.PUBLIC),
                ReflectionUtils.withPrefix("is"),
                ReflectionUtils.withParametersCount(0));

        for (Method method : methods) {
            if (Modifier.isAbstract(method.getModifiers())
                    || Modifier.isInterface(method.getModifiers())) {
                continue;
            }
            getters.add(method);
        }

        return getters;
    }

    /**
     * Returns setters for the class.
     * @param clazz
     * @return set of setters
     */
    private static Set<Method> setters(Class clazz) {
        Set<Method> setters = new HashSet<>();

        Set<Method> methods = ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withModifier(Modifier.PUBLIC),
                ReflectionUtils.withPrefix("set"),
                ReflectionUtils.withParametersCount(1));

        for (Method method : methods) {
            if (Modifier.isAbstract(method.getModifiers())
                    || Modifier.isInterface(method.getModifiers())) {
                continue;
            }
            setters.add(method);
        }
        return setters;
    }

}
