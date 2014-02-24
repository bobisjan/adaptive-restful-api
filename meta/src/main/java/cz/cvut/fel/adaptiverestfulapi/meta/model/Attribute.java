
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.lang.reflect.Method;


/**
 * Represents entity's attribute.
 */
public class Attribute extends Property {

    public Attribute(String name, Method getter, Method setter) {
        super(name, getter, setter);
    }

}
