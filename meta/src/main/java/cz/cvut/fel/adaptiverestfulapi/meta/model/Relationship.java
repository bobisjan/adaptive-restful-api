
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.lang.reflect.Method;


/**
 * Represents entity's relationship with other entity.
 */
public class Relationship extends Property {

    private final String targetEntity;

    public Relationship(String name, Method getter, Method setter, String targetEntity) {
        this(name, name, getter, setter, targetEntity);
    }

    public Relationship(String name, String shortName, Method getter, Method setter, String targetEntity) {
        super(name, shortName, getter, setter);
        this.targetEntity = targetEntity;
    }

    public String getTargetEntity() {
        return this.targetEntity;
    }

}
