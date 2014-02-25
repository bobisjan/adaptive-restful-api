
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.util.*;


/**
 * Contains all metadata about model.
 */
public class Model {

    private final String name;
    private final Map<String, Entity> entities;

    public Model(String name, Collection<Entity> entities) {
        this.name = name;
        this.entities = new HashMap<>();

        for (Entity entity : entities) {
            this.entities.put(entity.getName(), entity);
        }
    }

    /**
     * Returns name of the model.
     * @return model name
     */
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

    /**
     * Returns entity for given name.
     * @param name
     * @return entity or null if not found
     */
    public Entity entityForName(String name) {
        if (name != null) {
            return this.entities.get(name);
        }
        return null;
    }

}
