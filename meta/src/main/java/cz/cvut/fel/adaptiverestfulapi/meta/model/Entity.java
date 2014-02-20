
package cz.cvut.fel.adaptiverestfulapi.meta.model;

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
        this.name = name;
        this.entityClass = entityClass;
    }

    public String getName() {
        return this.name;
    }

    public Class getEntityClass() {
        return this.entityClass;
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

    public void addAttribute(Attribute attribute) {
        if (attribute != null) {
            this.attributes.put(attribute.getName(), attribute);
        }
    }

    public void addRelationship(Relationship relationship) {
        if (relationship != null) {
            this.relationships.put(relationship.getName(), relationship);
        }
    }

}
