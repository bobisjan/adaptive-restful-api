
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.data.BaseClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class InspectorTest {

    @Test
    public void testInspectWithInvalidPackage() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        model = inspector.inspect("xyz");

        assertNull("Model should be null.", model);
    }

    @Test
    public void testInspectWithObject() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        model = inspector.inspect("cz.cvut.fel.adaptiverestfulapi.meta.data");

        assertNotNull("Model should not be null.", model);
        assertNotNull("Model should has entity named \"Person\"",  model.entityForName("Person"));
    }

    @Test
    public void testInspectWithBaseClass() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        model = inspector.inspect("cz.cvut.fel.adaptiverestfulapi.meta.data", BaseClass.class);

        assertNotNull("Model should not be null.", model);
        assertNotNull("Model should has entity named \"Person\"",  model.entityForName("Person"));
    }
}
