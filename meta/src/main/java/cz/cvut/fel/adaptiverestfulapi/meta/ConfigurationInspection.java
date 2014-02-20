
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Variable;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;

import java.util.List;


/**
 * Allows to provide configuration from different scopes.
 */
public interface ConfigurationInspection {

    /**
     * Returns global configuration variables.
     * @return variables
     */
    public List<Variable> configuration();

    /**
     * Returns configuration variables for the model.
     * @param model
     * @return variables
     */
    public List<Variable> configuration(Model model);

    /**
     * Returns configuration variables for the entity.
     * @param entity
     * @return configuration
     */
    public List<Variable> configuration(Entity entity);

    /**
     * Returns configuration variables for the attribute.
     * @param attribute
     * @return variables
     */
    public List<Variable> configuration(Attribute attribute);

    /**
     * Returns configuration variables for the relationship.
     * @param relationship
     * @return variables
     */
    public List<Variable> configuration(Relationship relationship);

}
