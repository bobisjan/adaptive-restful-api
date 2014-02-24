
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Builder for model.
 */
public class ModelBuilder {

    private final String name; // name of the model
    private final Class baseClass;

    private final Map<String, Entity> entities;
    private final Map<String, Map<String, Attribute>> attributes;
    private final Map<String, Map<String, Relationship>> relationships;

    private final List<String> errors;

    public ModelBuilder(String name, Class baseClass) {
        this.name = name;
        this.baseClass = baseClass;
        this.entities = new HashMap<>();
        this.attributes = new HashMap<>();
        this.relationships = new HashMap<>();
        this.errors = new LinkedList<>();
    }

    public Map<String, Entity> getEntities() {
        return this.entities;
    }

    public String getName() {
        return this.name;
    }

    public Class getBaseClass() {
        return this.baseClass;
    }

    public void addEntity(Entity entity) {
        if (entity != null) {
            if (this.isValid(entity)) {
                this.entities.put(entity.getName(), entity);
                this.attributes.put(entity.getName(), new HashMap<String, Attribute>());
                this.relationships.put(entity.getName(), new HashMap<String, Relationship>());
            }
        }
    }

    public void addAttribute(Attribute attribute, Entity entity) {
        if (this.isValid(attribute, entity)) {
            this.attributes.get(entity.getName()).put(attribute.getName(), attribute);
        }
    }

    public void addRelationship(Relationship relationship, Entity entity) {
        if (this.isValid(relationship, entity)) {
            this.relationships.get(entity.getName()).put(relationship.getName(), relationship);
        }
    }

    protected boolean isValid(Entity entity) {
        boolean valid = true;

        if (entity.getName() == null || entity.getName().isEmpty()) {
            this.addError("Entity name " + entity.getName() + " is not valid.");
            valid = false;
        }
        if (entity.getEntityClass() == null) {
            this.addError("Entity class " + entity.getEntityClass() + " is not valid.");
            valid = false;
        }
        // TODO check for duplicates?
        return valid;
    }

    protected boolean isValid(Attribute attribute, Entity entity) {
        boolean valid = true;

        if (attribute.getName() == null || attribute.getName().isEmpty()) {
            this.addError("Entity " + entity.getName() + " : Attribute name " + attribute.getName() + " is not valid.");
            valid = false;
        }
        // TODO check for duplicates?
        return valid;
    }

    protected boolean isValid(Relationship relationship, Entity entity) {
        boolean valid = true;

        if (relationship.getName() == null || relationship.getName().isEmpty()) {
            this.addError("Entity " + entity.getName() + "Relationship name " + relationship.getName() + " is not valid.");
            valid = false;
        }
        // TODO check for duplicates?
        return valid;
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public Model build(Logger logger) {
        if (this.entities.size() == 0) {
            this.addError("There are no entities in the model \"" + this.name + "\"");
        }

        if (this.hasErrors()) {
            if (logger != null) {
                for (String error : this.errors) {
                    logger.error(error);
                }
            }
            return null;
        }

        Map<String, Entity> entities = new HashMap<>();
        for (Entity entity : this.entities.values()) {
            Entity e = new Entity(entity.getName(),
                    entity.getEntityClass(),
                    this.attributes.get(entity.getName()),
                    this.relationships.get(entity.getName()));

            entities.put(e.getName(), e);
        }
        return new Model(this.name, entities);
    }

}
