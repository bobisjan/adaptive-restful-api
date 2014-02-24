
package cz.cvut.fel.adaptiverestfulapi.meta.configuration;


/**
 * Configuration variable.
 */
public class Variable {

    public final String key;
    public final Object value;

    public Variable(String key, Object value) {
        this.key = key;
        this.value = value;
    }

}
