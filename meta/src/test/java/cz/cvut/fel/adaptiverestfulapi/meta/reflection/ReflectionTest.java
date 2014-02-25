package cz.cvut.fel.adaptiverestfulapi.meta.reflection;

import cz.cvut.fel.adaptiverestfulapi.meta.ConfigurationInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.ModelInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.data.Provider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;


public class ReflectionTest {

    @Test(dataProvider = "packages", dataProviderClass = Provider.class)
    public void testLeafs(String pack, Class baseClass, ModelInspectionListener modeler, ConfigurationInspectionListener configurator) throws Exception {
        Reflection reflection = new Reflection(pack, baseClass);

        Set<Class<?>> leafs = reflection.leafs(baseClass);

        assert (leafs.size() > 0) : "There should be leaf classes.";
        assert (leafs.size() == 2) : "There should 2 leaf classes.";
    }

    @Test(dataProvider = "packages", dataProviderClass = Provider.class)
    public void testTriplets(String pack, Class baseClass, ModelInspectionListener modeler, ConfigurationInspectionListener configurator) throws Exception {
        Reflection reflection = new Reflection(pack, baseClass);
        Set<Class<?>> leafs = reflection.leafs(baseClass);

        for (Class<?> clazz : leafs) {
            Set<Triplet<Field, Method, Method>> triplets = reflection.triplets(clazz);

            assert (triplets.size() == 2) : "Class " + clazz.getCanonicalName() + " should have 2 triplets, has " + triplets.size() + ".";
        }
    }

}
