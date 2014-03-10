
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.lang.reflect.Method;


/**
 * Represents entity's attribute.
 */
public class Attribute extends Property {

    private boolean primary;

    public Attribute(String name, Method getter, Method setter) {
        this(name, getter, setter, false);
    }

    public Attribute(String name, Method getter, Method setter, boolean primary) {
        this(name, name, getter, setter, primary);
    }

    public Attribute(String name, String shortName, Method getter, Method setter, boolean primary) {
        super(name, shortName, getter, setter);
        this.primary = primary;
    }

    public boolean isPrimary() {
        return this.primary;
    }

}
