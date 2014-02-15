
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.data.BaseClass;
import org.testng.annotations.Test;


public class InspectorTest {

    @Test
    public void testInspectWithInvalidPackage() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        model = inspector.inspect("xyz");

        assert (model == null) : "Model should be null.";
    }

    @Test
    public void testInspectWithObject() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        model = inspector.inspect("cz.cvut.fel.adaptiverestfulapi.meta.data");

        assert (model != null) : "Model should not be null.";
        assert (model.entityForName("Person") != null) : "Model should has entity named \"Person\".";
    }

    @Test
    public void testInspectWithBaseClass() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        model = inspector.inspect("cz.cvut.fel.adaptiverestfulapi.meta.data", BaseClass.class);

        assert (model != null) : "Model should not be null.";
        assert (model.entityForName("Person") != null) : "Model should has entity named \"Person\".";
    }
}
