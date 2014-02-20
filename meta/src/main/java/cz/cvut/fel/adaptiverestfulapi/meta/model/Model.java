
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Contains all metadata about model.
 */
public class Model {

    private String name;
    private Map<String, Entity> entities;

    public Model(String name) {
        this.name = name;
        this.entities = new HashMap();
    }

    public String getName() {
        return this.name;
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
