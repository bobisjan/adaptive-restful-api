
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents model entity.
 */
public class Entity {

    private final String name;
    private final Class entityClass;

    private Map<String, Attribute> attributes;
    private Map<String, Relationship> relationships;

    public Entity(String name, Class entityClass) {
        this(name, entityClass, new HashMap<String, Attribute>(), new HashMap<String, Relationship>());
    }

    public Entity(String name, Class entityClass, Map<String, Attribute> attributes, Map<String, Relationship> relationships) {
        this.name = name;
        this.entityClass = entityClass;
        this.attributes = attributes;
        this.relationships = relationships;
    }

    public String getName() {
        return this.name;
    }

    public Class getEntityClass() {
        return this.entityClass;
    }

    public Map<String, Attribute> getAttributes() {
        return this.attributes;
    }

    public Map<String, Relationship> getRelationships() {
        return this.relationships;
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
