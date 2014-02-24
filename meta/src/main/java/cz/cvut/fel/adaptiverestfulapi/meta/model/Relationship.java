
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.lang.reflect.Method;


/**
 * Represents entity's relationship with other entities.
 */
public class Relationship extends Property {

    public Relationship(String name, Method getter, Method setter) {
        super(name, getter, setter);
    }

}
