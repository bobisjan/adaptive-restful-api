
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.lang.reflect.Method;
import java.lang.reflect.Type;


/**
 * Represents entity's attribute.
 */
public class Attribute extends Property {

    private Type attributeType;
    private boolean primary;

    public Attribute(String name, Method getter, Method setter, Type attributeType) {
        this(name, getter, setter, attributeType, false);
    }

    public Attribute(String name, Method getter, Method setter, Type attributeType, boolean primary) {
        this(name, name, getter, setter, attributeType, primary);
    }

    public Attribute(String name, String shortName, Method getter, Method setter, Type attributeType, boolean primary) {
        super(name, shortName, getter, setter);
        this.attributeType = attributeType;
        this.primary = primary;
    }

    public Type getAttributeType() {
        return this.attributeType;
    }

    public boolean isPrimary() {
        return this.primary;
    }

}
