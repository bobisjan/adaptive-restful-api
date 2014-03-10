
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.lang.reflect.Method;


/**
 * Represents entity's relationship with other entity.
 */
public class Relationship extends Property {

    private final String targetEntity;
    private final RelationshipType relationshipType;

    public Relationship(String name, Method getter, Method setter, String targetEntity, RelationshipType relationshipType) {
        this(name, name, getter, setter, targetEntity, relationshipType);
    }

    public Relationship(String name, String shortName, Method getter, Method setter, String targetEntity, RelationshipType relationshipType) {
        super(name, shortName, getter, setter);
        this.targetEntity = targetEntity;
        this.relationshipType = relationshipType;
    }

    public String getTargetEntity() {
        return this.targetEntity;
    }

    public boolean isToOne() {
        return RelationshipType.ToOne.equals(this.relationshipType);
    }

    public boolean isToMany() {
        return RelationshipType.ToMany.equals(this.relationshipType);
    }

}
