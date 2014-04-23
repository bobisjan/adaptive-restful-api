
package cz.cvut.fel.adaptiverestfulapi.core;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Pack;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import org.testng.annotations.Test;

import java.util.LinkedList;


public class ApplicationContextTest {

    @Test
    public void testInitializeInvalid() throws Exception {
        ApplicationContext.initialize(null, null);
        ApplicationContext context = ApplicationContext.getInstance();
        assert (context.isInitialized() == false) : "Application context should not be initialized.";
    }

    @Test
    public void testInitializeValid() throws Exception {
        Model model = new Model("model", new LinkedList<Entity>());
        Configuration conf = new Pack();

        ApplicationContext.initialize(model, conf);

        ApplicationContext context = ApplicationContext.getInstance();
        assert (context.isInitialized() == true) : "Application context should be initialized.";
    }

}
