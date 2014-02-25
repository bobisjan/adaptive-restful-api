
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.data.Provider;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import org.testng.annotations.Test;


public class ModelInspectionTest {

    @Test
    public void testInspectionWithInvalidPackage() throws Exception {
        Inspector inspector = new Inspector();
        inspector.setModeler(new TestInspectionListener());

        Model model = null;
        model = inspector.model("xyz");

        assert (model == null) : "Model should be null.";
    }

    @Test(dataProvider = "packages", dataProviderClass = Provider.class)
    public void testInspection(String pack, Class baseClass, ModelInspectionListener modeler, ConfigurationInspectionListener configurator) throws Exception {
        Inspector inspector = new Inspector();
        inspector.setModeler(modeler);

        Model model = null;
        model = inspector.model(pack, baseClass);

        assert (model != null) : "Model should not be null.";

        Entity expected = null;

        expected = model.entityForName(pack + ".Project");
        assert (expected != null) : "Model should have entity named `Project`.";
        assert (expected.attributeForName(expected.getName() + ".name") != null) : "Entity `Project` should have attribute `name`.";
        assert (expected.attributeForName(expected.getName() + ".lowerCasedName") != null) : "Entity `Project` should have `setter only` attribute `lowerCasedName`.";
        assert (expected.attributeForName(expected.getName() + ".startedAt") != null) : "Entity `Project` should have attribute `startedAt`.";
        assert (expected.attributeForName(expected.getName() + ".started") != null) : "Entity `Project` should have `getter and setter` attribute `started`.";
        assert (expected.relationshipForName(expected.getName() + ".issues") != null) : "Entity `Project` should have relationship `issues`.";

        expected = model.entityForName(pack +  ".Issue");
        assert (expected != null) : "Model should have entity named `Issue`.";
        assert (expected.attributeForName(expected.getName() + ".description") != null) : "Entity `Issue` should have attribute `description`.";
        assert (expected.attributeForName(expected.getName() + ".localizedDescription") != null) : "Entity `Issue` should have `getter only` attribute `localizedDescription`.";
        assert (expected.relationshipForName(expected.getName() + ".project") != null) : "Entity `Issue` should have relationship `project`.";
    }

}
