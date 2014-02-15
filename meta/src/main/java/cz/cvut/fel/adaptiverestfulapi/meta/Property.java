
package cz.cvut.fel.adaptiverestfulapi.meta;


/**
 * Base class for entity's attributes and relationships.
 */
public abstract class Property {

    private final String name;

    public Property(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
