
package cz.cvut.fel.adaptiverestfulapi.meta;

import java.util.HashMap;
import java.util.Map;


/**
 * Contains all metadata about model.
 */
public class Model {

    private Configuration configuration;

    private Map<String, Entity> entities;

    public Model(Configuration configuration) {
        this.configuration = configuration;
        this.entities = new HashMap();
    }

    public Map<String, Entity> getEntities() {
        return this.entities;
    }

    public Entity entityForName(String name) {
        if (name != null) {
            return this.entities.get(name);
        }
        return null;
    }

}
