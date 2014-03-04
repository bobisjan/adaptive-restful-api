
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.core.ApplicationContext;
import cz.cvut.fel.adaptiverestfulapi.meta.InspectionException;
import cz.cvut.fel.adaptiverestfulapi.meta.Inspector;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext ctx = ApplicationContext.getInstance();

        if (!ctx.isInitialized()) {
            Inspector inspector = new Inspector();

            inspector.setModeler(new ModelListener());
            inspector.addConfigurator(new ConfigurationListener());

            try {
                Model model = inspector.model("cz.cvut.fel.adaptiverestfulapi.example.model");
                Configuration configuration = inspector.configuration(model);
                ApplicationContext.initialize(model, configuration);

            } catch (InspectionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) { }

}
