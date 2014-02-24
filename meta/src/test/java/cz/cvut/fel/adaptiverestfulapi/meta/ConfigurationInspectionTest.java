
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.data.Provider;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import org.testng.annotations.Test;


public class ConfigurationInspectionTest {

    @Test(dataProvider = "packages", dataProviderClass = Provider.class)
    public void testInspection(String pack, Class baseClass, ModelInspectionListener modeler, ConfigurationInspectionListener configurator) throws Exception {
        Inspector inspector = new Inspector();

        inspector.setModeler(modeler);
        inspector.setConfigurator(configurator);

        Model model = inspector.model(pack, baseClass);
        Configuration configuration = inspector.configuration(model);

        assert (configuration != null) : "Configuration should not be null.";
    }

}
