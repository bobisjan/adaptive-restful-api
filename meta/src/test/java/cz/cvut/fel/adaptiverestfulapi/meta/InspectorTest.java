
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.data.BaseClass;
import org.testng.annotations.Test;

import java.util.HashMap;


public class InspectorTest {

    @Test
    public void testInspectWithInvalidPackage() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        Configuration configuration = new Configuration(new HashMap<String, Object>());
        model = inspector.inspect("xyz", configuration);

        assert (model == null) : "Model should be null.";
    }

    @Test
    public void testInspectWithObject() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        Configuration configuration = new Configuration(new HashMap<String, Object>());
        model = inspector.inspect("cz.cvut.fel.adaptiverestfulapi.meta.data", configuration);

        assert (model != null) : "Model should not be null.";
        assert (model.entityForName("Person") != null) : "Model should has entity named \"Person\".";
    }

    @Test
    public void testInspectWithBaseClass() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        Configuration configuration = new Configuration(new HashMap<String, Object>());
        model = inspector.inspect("cz.cvut.fel.adaptiverestfulapi.meta.data", BaseClass.class, configuration);

        assert (model != null) : "Model should not be null.";
        assert (model.entityForName("Person") != null) : "Model should has entity named \"Person\".";
    }
}
