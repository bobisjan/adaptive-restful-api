
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.lang.reflect.Method;


/**
 * Represents entity's relationship with other entities.
 */
public class Relationship extends Property {

    private final Entity targetEntity;

    public Relationship(String name, Method getter, Method setter, Entity targetEntity) {
        super(name, getter, setter);
        this.targetEntity = targetEntity;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }

}
