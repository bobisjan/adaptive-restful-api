
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.data.Provider;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import org.testng.annotations.Test;


public class InspectorTest {

    @Test
    public void testInspectionWithInvalidPackage() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        model = inspector.model("xyz");

        assert (model == null) : "Model should be null.";
    }

    @Test(dataProvider = "packages", dataProviderClass = Provider.class)
    public void testInspection(String pack, Class baseClass) throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        model = inspector.model(pack, baseClass);

        assert (model != null) : "Model should not be null.";
        assert (model.entityForName("Project") != null) : "Model should has entity named \"Project\".";
        assert (model.entityForName("Issue") != null) : "Model should has entity named \"Issue\".";
    }

}
