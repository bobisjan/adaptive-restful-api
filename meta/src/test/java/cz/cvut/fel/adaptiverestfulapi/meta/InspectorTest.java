
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.data.Provider;
import org.testng.annotations.Test;
import java.util.HashMap;


public class InspectorTest {

    @Test
    public void testInspectionWithInvalidPackage() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        Configuration configuration = new Configuration(new HashMap<String, Object>());
        model = inspector.inspect("xyz", configuration);

        assert (model == null) : "Model should be null.";
    }

    @Test(dataProvider = "packages", dataProviderClass = Provider.class)
    public void testInspection(String pack, Class baseClass, Configuration configuration) throws Exception {
        Inspector inspector = new Inspector();
        inspector.setListener(new Listener());

        Model model = null;
        model = inspector.inspect(pack, baseClass, configuration);

        assert (model != null) : "Model should not be null.";
        assert (model.entityForName("Project") != null) : "Model should has entity named \"Project\".";
        assert (model.entityForName("Issue") != null) : "Model should has entity named \"Issue\".";
    }

}
