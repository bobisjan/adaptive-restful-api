
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.data.Provider;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Reflection;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;
import org.reflections.Reflections;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;


public class RelationshipTest {

    @Test(dataProvider = "packages", dataProviderClass = Provider.class)
    public void testToOneCanBeInstantiated(String pack, Class baseClass) throws Exception {
        Reflections reflections = Reflection.reflections(pack, baseClass);
        Set<Class<?>> leafs = Reflection.leafs(reflections, baseClass);

        for (Class<?> clazz : leafs) {
            Set<Triplet<Field, Method, Method>> triplets = Reflection.triplets(clazz);
            for (Triplet<Field, Method, Method> triplet : triplets) {
                Class<?> dclazz = null;

                if (triplet.a != null) {
                    dclazz = triplet.a.getDeclaringClass();

                } else if (triplet.c != null) {
                    Class<?>[] parameters = triplet.c.getParameterTypes();
                    dclazz = parameters[0].getDeclaringClass();

                } else {
                    // Instantiation is not required...
                    continue;
                }

                // Test entity classes only...
                if (leafs.contains(dclazz)) {
                    try {
                        assert (dclazz.newInstance() != null) : "Can not create instance of " + dclazz.getName() + " from " + clazz.getSimpleName()  + ".";

                    } catch (InstantiationException e) {
                        assert (false) : e.getLocalizedMessage();

                    } catch (IllegalAccessException e) {
                        assert (false) : e.getLocalizedMessage();
                    }
                }
            }
        }
    }

    @Test(dataProvider = "packages", dataProviderClass = Provider.class)
    public void testToManyCanBeInstantiated(String pack, Class baseClass) throws Exception {
        Reflections reflections = Reflection.reflections(pack, baseClass);
        Set<Class<?>> leafs = Reflection.leafs(reflections, baseClass);

        for (Class<?> clazz : leafs) {
            Set<Triplet<Field, Method, Method>> triplets = Reflection.triplets(clazz);
            for (Triplet<Field, Method, Method> triplet : triplets) {
                Class<?> dclazz = null;

                if (triplet.a != null) {
                    Class<?> tmp = triplet.a.getType();

                    if (Collection.class.isAssignableFrom(tmp)) {
                        Type type = triplet.a.getGenericType();

                        if (type instanceof ParameterizedType) {
                            ParameterizedType pType = (ParameterizedType)type;
                            Type[] arr = pType.getActualTypeArguments();
                            dclazz = (Class<?>)arr[0];
                        }
                    }

                } else if (triplet.c != null) {
                    Class<?>[] parameters = triplet.c.getParameterTypes();

                    if (Collection.class.isAssignableFrom(parameters[0])) {
                        Type[] types = triplet.c.getGenericParameterTypes();

                        if (types[0] instanceof ParameterizedType) {
                            ParameterizedType pType = (ParameterizedType)types[0];
                            Type[] arr = pType.getActualTypeArguments();
                            dclazz = (Class<?>)arr[0];
                        }
                    }

                } else {
                    // Instantiation is not required...
                    continue;
                }

                // Test entity classes only...
                if (leafs.contains(dclazz)) {
                    try {
                        assert (dclazz.newInstance() != null) : "Can not create instance of " + dclazz.getName() + " from collection from " + clazz.getSimpleName()  + ".";

                    } catch (InstantiationException e) {
                        assert (false) : e.getLocalizedMessage();

                    } catch (IllegalAccessException e) {
                        assert (false) : e.getLocalizedMessage();
                    }
                }
            }
        }
    }

}
