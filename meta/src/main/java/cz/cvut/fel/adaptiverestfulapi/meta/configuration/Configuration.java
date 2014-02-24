
package cz.cvut.fel.adaptiverestfulapi.meta.configuration;


import cz.cvut.fel.adaptiverestfulapi.meta.model.*;

/**
 * Configuration holds metadata about the model, entities and properties.
 */
public interface Configuration {

    /**
     * Returns global configuration value.
     * @param key
     * @param <T> type of value
     * @return value
     */
    public <T> T get(String key);

    /**
     * Returns model configuration value.
     * @param key
     * @param model
     * @param <T> type of value
     * @return value
     */
    public <T> T get(String key, Model model);

    /**
     * Returns entity configuration value.
     * @param key
     * @param entity
     * @param <T> type of value
     * @return value
     */
    public <T> T get(String key, Entity entity);

    /**
     * Returns attribute configuration value.
     * @param key
     * @param attribute
     * @param <T> type of value
     * @return value
     */
    public <T> T get(String key, Attribute attribute);

    /**
     * Returns relationship configuration value.
     * @param key
     * @param relationship
     * @param <T> type of value
     * @return value
     */
    public <T> T get(String key, Relationship relationship);

}
