
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.lang.reflect.Method;


/**
 * Represents entity's relationship with other entities.
 */
public class Relationship extends Property {

    private final String targetEntity;

    public Relationship(String name, Method getter, Method setter, String targetEntity) {
        super(name, getter, setter);
        this.targetEntity = targetEntity;
    }

    public String getTargetEntity() {
        return this.targetEntity;
    }

}
