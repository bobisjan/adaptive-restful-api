
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
        inspector.addConfigurator(configurator);

        Model model = inspector.model(pack, baseClass);
        Configuration configuration = inspector.configuration(model);

        assert (configuration != null) : "Configuration should not be null.";
        assert (configuration.get("xyz") == null) : "Value for `xyz` should be null.";

        // chain test
        assert ("JSON".equalsIgnoreCase((String)configuration.get("Serialization"))) : "Serialization should be set to JSON.";
        assert ("JSON".equalsIgnoreCase((String)configuration.get("Serialization",
                model.entityForName(pack + ".Project").attributeForName(pack + ".Project.name")))) : "Serialization should be set (chained) to JSON on the attribute `Project.name`.";

        // more specific wins test
        assert ("a".equalsIgnoreCase((String)configuration.get("ABC", model))) : "ABC should be set to `a` on the model.";
        assert ("z".equalsIgnoreCase((String)configuration.get("ABC",
                model.entityForName(pack + ".Issue").attributeForName(pack + ".Issue.description")))) : "ABC should be set to `z` on the attribute `Issue.description`.";
    }

}
