
package cz.cvut.fel.adaptiverestfulapi.meta.configuration;


/**
 * Configuration variable.
 * Generally scope could be a property (attribute or relationship), an entity or a model.
 * @param <T> scope of variable
 */
public class Variable<T> {

    public String key;
    public Object value;
    public T scope;

}
