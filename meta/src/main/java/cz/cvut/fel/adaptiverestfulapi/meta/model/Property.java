
package cz.cvut.fel.adaptiverestfulapi.meta.model;

import java.lang.reflect.Method;


/**
 * Base class for entity's attributes and relationships.
 */
public abstract class Property {

    private final String name;
    private final String shortName;
    private final Method getter;
    private final Method setter;

    public Property(String name, Method getter, Method setter) {
        this(name, name, getter, setter);
    }

    public Property(String name, String shortName, Method getter, Method setter) {
        this.name = name;
        this.shortName = shortName;
        this.getter = getter;
        this.setter = setter;
    }

    /**
     * Returns name of the property.
     * @return property name
     */
    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Method getGetter() {
        return this.getter;
    }

    public Method getSetter() {
        return this.setter;
    }

}
