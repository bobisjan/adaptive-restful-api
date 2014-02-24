
package cz.cvut.fel.adaptiverestfulapi.meta.configuration;

import cz.cvut.fel.adaptiverestfulapi.meta.model.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Pack provides access for configuration within hierarchical view.
 */
public class Pack implements Configuration {

    private static final String GLOBAL = "__global";
    private static final String PARENT = "__parent";

    private Map<String, Map<String, Object>> data;

    /**
     * Creates pack with empty global configuration
     */
    public Pack() {
        this(new LinkedList<Variable>());
    }

    /**
     * Creates pack with global configuration.
     * @param variables
     */
    public Pack(List<Variable> variables) {
        this.data = new HashMap<>();

        Map<String, Object> map = new HashMap<>();
        for (Variable v : variables) {
            map.put(v.key, v.value);
        }
        this.data.put(GLOBAL, map);
    }

    private void addConfiguration(List<Variable> variables, String scope, String parent) {
        if (!this.data.containsKey(parent)) {
            // TODO throw `invalid parent` exception?
            return;
        }

        Map<String, Object> map = new HashMap<>();

        map.put(PARENT, parent);

        for (Variable v : variables) {
            map.put(v.key, v.value);
        }
        this.data.put(scope, map);
    }

    /**
     * Add variables for model under the global level.
     * @param variables
     * @param model
     */
    public void addConfiguration(List<Variable> variables, Model model) {
        this.addConfiguration(variables, model.getName(), GLOBAL);
    }

    /**
     * Add variables for model under the parent model.
     * @param variables
     * @param model
     * @param parent
     */
    public void addConfiguration(List<Variable> variables, Model model, Model parent) {
        this.addConfiguration(variables, model.getName(), parent.getName());
    }

    /**
     * Add variables for entity under the model.
     * @param variables
     * @param entity
     * @param model
     */
    public void addConfiguration(List<Variable> variables, Entity entity, Model model) {
        this.addConfiguration(variables, entity.getName(), model.getName());
    }

    /**
     * Add variables for attribute under the entity.
     * @param variables
     * @param attribute
     * @param entity
     */
    public void addConfiguration(List<Variable> variables, Attribute attribute, Entity entity) {
        this.addConfiguration(variables, attribute.getName(), entity.getName());
    }

    /**
     * Add variables for relationship under the entity.
     * @param variables
     * @param relationship
     * @param entity
     */
    public void addConfiguration(List<Variable> variables, Relationship relationship, Entity entity) {
        this.addConfiguration(variables, relationship.getName(), entity.getName());
    }

    private <T> T get(String key, String scope) {
        if (!this.data.containsKey(scope)) {
            // TODO throw `invalid scope` exception?
            return null;
        }

        Map<String, Object> map = this.data.get(scope);

        // current scope has the value
        if (map.containsKey(key)) {
            return (T)map.get(key);
        }

        // chain the process of getting value
        if (map.containsKey(PARENT)) {
            return this.get(key, (String)map.get(PARENT));
        }

        // TODO throw `not found` exception?
        return null;
    }

    @Override
    public <T> T get(String key) {
        return this.get(key, GLOBAL);
    }

    @Override
    public <T> T get(String key, Model model) {
        return this.get(key, model.getName());
    }

    @Override
    public <T> T get(String key, Entity entity) {
        return this.get(key, entity.getName());
    }

    @Override
    public <T> T get(String key, Attribute attribute) {
        return this.get(key, attribute.getName());
    }

    @Override
    public <T> T get(String key, Relationship relationship) {
        return this.get(key, relationship.getName());
    }

}
