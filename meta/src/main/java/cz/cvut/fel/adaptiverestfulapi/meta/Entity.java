
package cz.cvut.fel.adaptiverestfulapi.meta;


/**
 * Represents model entity.
 */
public class Entity {

    private final String name;
    private final Class entityClass;

    public Entity(String name, Class entityClass) {
        this.name = name;
        this.entityClass = entityClass;
    }

    public String getName() {
        return this.name;
    }

    public Class getEntityClass() {
        return this.entityClass;
    }

}
