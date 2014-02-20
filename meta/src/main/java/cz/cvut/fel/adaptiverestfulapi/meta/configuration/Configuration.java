
package cz.cvut.fel.adaptiverestfulapi.meta.configuration;


/**
 * Configuration holds metadata about the model, entities and properties.
 */
public interface Configuration {

    public <T> T get(String key, String scope);

}
