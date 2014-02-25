
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.util.*;


/**
 * Represents model entity.
 */
public class Entity {

    private final String name;
    private final Class entityClass;

    private Map<String, Attribute> attributes;
    private Map<String, Relationship> relationships;

    /**
     * Creates empty entity.
     * You use this constructor in the model inspection process.
     * @param name
     * @param entityClass
     */
    public Entity(String name, Class entityClass) {
        this(name, entityClass, new LinkedList<Attribute>(), new LinkedList<Relationship>());
    }

    /**
     * Creates fully prepared entity.
     * This constructor is used by the model builder.
     * @param name
     * @param entityClass
     * @param attributes
     * @param relationships
     */
    public Entity(String name, Class entityClass, Collection<Attribute> attributes, Collection<Relationship> relationships) {
        this.name = name;
        this.entityClass = entityClass;

        this.attributes = new HashMap<>();
        for (Attribute attribute : attributes) {
            this.attributes.put(attribute.getName(), attribute);
        }

        this.relationships = new HashMap<>();
        for (Relationship relationship : relationships) {
            this.relationships.put(relationship.getName(), relationship);
        }
    }

    /**
     * Returns name of the entity.
     * @return entity name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns class of the entity.
     * @return entity class
     */
    public Class getEntityClass() {
        return this.entityClass;
    }

    /**
     * Returns map of the attributes.
     * @return attributes
     */
    public Map<String, Attribute> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    /**
     * Returns map of the relationships.
     * @return
     */
    public Map<String, Relationship> getRelationships() {
        return Collections.unmodifiableMap(this.relationships);
    }

    public Attribute attributeForName(String name) {
        if (name != null) {
            return this.attributes.get(name);
        }
        return null;
    }

    public Relationship relationshipForName(String name) {
        if (name != null) {
            return this.relationships.get(name);
        }
        return null;
    }

}
