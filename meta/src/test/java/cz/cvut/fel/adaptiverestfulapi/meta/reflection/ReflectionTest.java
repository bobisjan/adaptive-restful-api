package cz.cvut.fel.adaptiverestfulapi.meta.reflection;

import cz.cvut.fel.adaptiverestfulapi.meta.ConfigurationInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.ModelInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.data.Provider;
import org.testng.annotations.Test;

import java.util.Set;


public class ReflectionTest {

    @Test(dataProvider = "packages", dataProviderClass = Provider.class)
    public void testLeafs(String pack, Class baseClass, ModelInspectionListener modeler, ConfigurationInspectionListener configurator) throws Exception {
        Reflection reflection = new Reflection(pack, baseClass);

        Set<Class<?>> leafs = reflection.leafs(baseClass);

        assert (leafs.size() > 0) : "There should be leaf classes.";
        assert (leafs.size() == 2) : "There should 2 leaf classes.";
    }

    @Test
    public void testTriplets() throws Exception {
        // TODO write test
    }

}
