
package cz.cvut.fel.adaptiverestfulapi.meta.configuration;

import cz.cvut.fel.adaptiverestfulapi.meta.model.*;

import java.util.Map;
import java.util.HashMap;


/**
 * Configuration holds metadata about the model, entities and properties.
 */
public interface Configuration {

    public <T> T get(String key, Model model);

    public <T> T get(String key, Entity entity);

    public <T> T get(String key, Attribute attribute);

    public <T> T get(String key, Relationship relationship);

}
