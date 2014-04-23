
package cz.cvut.fel.adaptiverestfulapi.core;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


public class ApplicationContext {

    private static ApplicationContext instance;

    private Model model;
    private Configuration configuration;

    public static void initialize(Model model, Configuration configuration) {
        ApplicationContext.getInstance().model = model;
        ApplicationContext.getInstance().configuration = configuration;
    }

    public static ApplicationContext getInstance() {
        // TODO fix thread-safety
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    private ApplicationContext() { }

    public boolean isInitialized() {
        return (this.model != null && this.configuration != null);
    }

    public Model getModel() {
        return this.model;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

}
