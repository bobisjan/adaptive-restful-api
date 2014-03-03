
package cz.cvut.fel.adaptiverestfulapi.core;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;


public class ApplicationContext {

    private static ApplicationContext instance;

    private boolean initialized;
    private Model model;
    private Configuration configuration;

    public static void initialize(Model model, Configuration configuration) {
        ApplicationContext.getInstance().model = model;
        ApplicationContext.getInstance().configuration = configuration;
        ApplicationContext.getInstance().initialized = true;
    }

    public static ApplicationContext getInstance() {
        // TODO fix thread-safety
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    private ApplicationContext() {
        this.initialized = false;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public Model getModel() {
        return this.model;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

}
