
package cz.cvut.fel.adaptiverestfulapi.meta.configuration;

import java.util.Map;
import java.util.HashMap;


/**
 * Configuration holds metadata about the model, entities and properties.
 */
public class Configuration {

    private final Map<String, Object> map;

    public Configuration(Map<String, Object> map) {
        this.map = new HashMap<>();
        this.map.putAll(map);
    }

    public Object get(String key) {
        return this.map.get(key);
    }

    // TODO implement helper methods, like getString(), getBoolean(), ...

}
