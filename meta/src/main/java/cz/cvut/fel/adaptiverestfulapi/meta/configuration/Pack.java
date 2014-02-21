
package cz.cvut.fel.adaptiverestfulapi.meta.configuration;

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

    /**
     * Add variables for scope under the global level.
     * @param variables
     * @param scope
     */
    public void addConfiguration(List<Variable> variables, String scope) {
        this.addConfiguration(variables, scope, GLOBAL);
    }

    /**
     * Adds variables for the scope under the parent level.
     * @param variables
     * @param scope
     * @param parent scope
     */
    public void addConfiguration(List<Variable> variables, String scope, String parent) {
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

    @Override
    public <T> T get(String key, String scope) {
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

}
