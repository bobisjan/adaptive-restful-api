
package cz.cvut.fel.adaptiverestfulapi.meta;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Contains all metadata about model.
 */
public class Model {

    private Map<String, Entity> entities;

    public Model() {
        this.entities = new HashMap();
    }

    /**
     * Returns an immutable map of entities.
     * @return entities
     */
    public Map<String, Entity> getEntities() {
        return Collections.unmodifiableMap(this.entities);
    }

    public void addEntity(Entity entity) {
        this.entities.put(entity.getName(), entity);
    }

    public Entity entityForName(String name) {
        if (name != null) {
            return this.entities.get(name);
        }
        return null;
    }

}
