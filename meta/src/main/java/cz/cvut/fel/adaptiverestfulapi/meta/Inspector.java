
package cz.cvut.fel.adaptiverestfulapi.meta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

import org.reflections.Reflections;
import org.reflections.ReflectionUtils;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * For given package name creates a meta model.
 */
public class Inspector {

    private InspectorListener listener;

    private Logger logger = LoggerFactory.getLogger(Inspector.class);

    private List<String> errors = new LinkedList<>();

    /**
     * Inspects given package for classes that extends Object class.
     * @param pack package to inspect
     * @param configuration global configuration
     * @return model
     */
    public Model inspect(String pack, Configuration configuration) throws InspectionException {
        return this.inspect(pack, java.lang.Object.class, configuration);
    }

    /**
     * Inspects given package for classes that extends passed class.
     * @param pack package to inspect
     * @param clazz base class
     * @param configuration global configuration
     * @return model
     */
    public Model inspect(String pack, Class clazz, Configuration configuration) throws InspectionException {
        if (pack == null || clazz == null) {
            throw new InspectionException("Package name, or base class are missing.");
        }

        Reflections reflections = this.reflections(pack, clazz);
        Set<Class<?>> clazzes = this.leafs(reflections, clazz);

        if (clazzes.size() == 0) {
            this.logger.warn("There are no classes in the package \"" + pack + "\"");
            return null;
        }

        Set<Entity> entities = new HashSet<>();

        // phase 1: inspect all leaf classes
        for (Class<?> k : clazzes) {
            Entity entity = this.listener.inspectEntity(k);
            if (entity != null) {
                if (this.isValid(entity)) {
                    entities.add(entity);

                } else {
                    this.errors.add("Entity for class " + k.getName() + " is not valid.");
                }
            }
        }

        // phase 2: inspect attributes and relationships
        for (Entity entity : entities) {
            Set<Triplet<Field, Method, Method>> triplets = this.match(entity.getEntityClass());
            for (Triplet<Field, Method, Method> triplet : triplets) {
                PropertyType type = this.typeOfProperty(triplet);
                if (type.equals(PropertyType.ATTRIBUTE)) {
                    Attribute attr = this.listener.inspectAttribute(triplet.a, triplet.b, triplet.c);
                    if (this.isValid(attr)) {
                        entity.addAttribute(attr);

                    } else {
                        this.errors.add("Attribute for " + triplet.toString() + " in entity " + entity.getName() + " is not valid");
                    }

                } else if (type.equals(PropertyType.RELATIONSHIP)) {
                    Relationship rel = this.listener.inspectRelationship(triplet.a, triplet.b, triplet.c);
                    if (this.isValid(rel)) {
                        entity.addRelationship(rel);

                    } else {
                        this.errors.add("Relationship for " + triplet.toString() + " in entity " + entity.getName() + " is not valid");
                    }

                } else {
                    this.errors.add("Could not resolve type of property " + triplet + " in entity " + entity.getName());
                }
            }
        }

        if (!errors.isEmpty()) {
            for (String error : this.errors) {
                this.logger.error(error);
            }
            return null;
        }

        Model model = new Model(configuration);

        for (Entity entity : entities) {
            model.addEntity(entity);
        }
        return model;
    }

    private Reflections reflections(String pack, Class clazz) {
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

    private Set<Class<?>> leafs(Reflections reflections, Class clazz) {
        Set<Class<?>> all = reflections.getSubTypesOf(clazz);
        Set<Class<?>> leafs = new HashSet<>();

        for (Class<?> c : all) {
            if (reflections.getSubTypesOf(c.getClass()).isEmpty()) {
                leafs.add(c);
            }
        }
        return leafs;
    }

    private Set<Triplet<Field, Method, Method>> match(Class clazz) {
        Set<Triplet<Field, Method, Method>> triplets = new HashSet<>();

        Set<Field> fields = this.fields(clazz);
        Set<Method> getters = this.getters(clazz);
        Set<Method> setters = this.setters(clazz);

        // TODO match field with getter and setter methods

        Method getter, setter;

        // step 1: add fields with getters, and setters (matched by field name)
        for (Field field : fields) {
            getter = this.getter(field, getters);
            setter = this.setter(field, setters);

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
            getter = this.getter(s, getters);

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

    private Set<Field> fields(Class clazz) {
        Set<Field> fields = new HashSet<>();

        fields.addAll(ReflectionUtils.getAllFields(clazz));
        return fields;
    }

    private Method getter(Field field, Set<Method> getters) {
        return this.getter(field.getName(), getters);
    }

    private Method getter(Method setter, Set<Method> getters) {
        return this.getter(setter.getName().substring(3), getters);
    }

    private Method getter(String name, Set<Method> getters) {
        for (Method getter : getters) {
            if (getter.getName().equalsIgnoreCase("is" + name.toUpperCase())
                    || getter.getName().equalsIgnoreCase("get" + name.toUpperCase())) {
                return getter;
            }
        }
        return null;
    }

    private Method setter(Field field, Set<Method> setters) {
        for (Method setter : setters) {
            if (setter.getName().equalsIgnoreCase("set" + field.getName().toUpperCase())) {
                return setter;
            }
        }
        return null;
    }

    private Set<Method> getters(Class clazz) {
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

    private Set<Method> setters(Class clazz) {
        Set<Method> setters = new HashSet<>();

        setters.addAll(ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withModifier(Modifier.PUBLIC),
                ReflectionUtils.withPrefix("set"),
                ReflectionUtils.withParametersCount(1)));
        return setters;
    }

    private PropertyType typeOfProperty(Triplet<Field, Method, Method> triplet) {
        // TODO resolve property type from triplet
        return PropertyType.UNKNOWN;
    }

    protected boolean isValid(Attribute attribute) {
        // TODO check for duplicates?
        if (attribute.getName() != null) {
            return true;
        }
        return false;
    }

    protected boolean isValid(Relationship relationship) {
        // TODO check for duplicates?
        if (relationship.getName() != null) {
            return true;
        }
        return false;
    }

    protected boolean isValid(Entity entity) {
        // TODO check for duplicates?
        if (entity.getName() != null && entity.getEntityClass() != null) {
            return true;
        }
        return false;
    }

    public void setListener(InspectorListener listener) {
        this.listener = listener;
    }

    private enum PropertyType {
        UNKNOWN,
        ATTRIBUTE,
        RELATIONSHIP
    }

    private class Triplet<A, B, C> {

        A a; B b; C c;

        Triplet(A a, B b, C c) {
            this.a = a; this.b = b; this.c = c;
        }

        @Override
        public String toString() {
            return "<" + this.a + ", " + this.b + ", " + this.c + ">";
        }

    }

}
