
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.meta.ConfigurationInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Variable;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;
import cz.cvut.fel.adaptiverestfulapi.data.Dispatcher;

import java.util.LinkedList;
import java.util.List;


public class ConfigurationListener implements ConfigurationInspectionListener {

    @Override
    public List<Variable> configuration() {
        List<Variable> vars = new LinkedList<>();
        vars.add(new Variable(Dispatcher.Handler, new DescriptionDataHandler()));
        return vars;
    }

    @Override
    public List<Variable> configuration(Model model) {
        return new LinkedList<>();
    }

    @Override
    public List<Variable> configuration(Entity entity) {
        return new LinkedList<>();
    }

    @Override
    public List<Variable> configuration(Attribute attribute) {
        return new LinkedList<>();
    }

    @Override
    public List<Variable> configuration(Relationship relationship) {
        return new LinkedList<>();
    }

}
