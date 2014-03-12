
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.data.DeleteHandler;
import cz.cvut.fel.adaptiverestfulapi.data.GetHandler;
import cz.cvut.fel.adaptiverestfulapi.data.PostHandler;
import cz.cvut.fel.adaptiverestfulapi.data.PutHandler;
import cz.cvut.fel.adaptiverestfulapi.meta.ConfigurationInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Variable;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;
import cz.cvut.fel.adaptiverestfulapi.serialization.application.json.JsonSerializer;
import cz.cvut.fel.adaptiverestfulapi.serialization.text.plain.PlainTextSerializer;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;


public class ConfigurationListener implements ConfigurationInspectionListener {

    @Override
    public List<Variable> configuration() {
        EntityManager entityManager = PersistenceContext.getInstance().getManager();

        List<Variable> vars = new LinkedList<>();
        vars.add(new Variable(GetHandler.Key, new cz.cvut.fel.adaptiverestfulapi.data.persistence.GetHandler(entityManager)));
        vars.add(new Variable(PostHandler.Key, new cz.cvut.fel.adaptiverestfulapi.data.persistence.PostHandler(entityManager)));
        vars.add(new Variable(PutHandler.Key, new cz.cvut.fel.adaptiverestfulapi.data.persistence.PutHandler(entityManager)));
        vars.add(new Variable(DeleteHandler.Key, new cz.cvut.fel.adaptiverestfulapi.data.persistence.DeleteHandler(entityManager)));

        vars.add(new Variable(PlainTextSerializer.MIME, new PlainTextSerializer()));
        vars.add(new Variable(JsonSerializer.MIME, new JsonSerializer()));
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
