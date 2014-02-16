
package cz.cvut.fel.adaptiverestfulapi.meta;


/**
 * Base class for entity's attributes and relationships.
 */
public abstract class Property {

    private final String name;
    private final Configuration configuration;

    public Property(String name, Configuration configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    public String getName() {
        return this.name;
    }

}
