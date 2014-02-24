
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

    private final Map<String, Entity> entities;

    private final List<String> errors;

    public ModelBuilder(String name) {
        this.name = name;
        this.entities = new HashMap<>();
        this.errors = new LinkedList<>();
    }

    public Map<String, Entity> getEntities() {
        return this.entities;
    }

    public void addEntity(Entity entity) {
        if (entity != null) {
            if (this.isValid(entity)) {
                this.entities.put(entity.getName(), entity);
            }
        }
    }

    public void addAttribute(Attribute attribute, Entity entity) {
        if (this.isValid(attribute, entity)) {
            entity.addAttribute(attribute);
        }
    }

    public void addRelationship(Relationship relationship, Entity entity) {
        if (this.isValid(relationship, entity)) {
            entity.addRelationship(relationship);
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
        if (this.hasErrors()) {
            if (logger != null) {
                for (String error : this.errors) {
                    logger.error(error);
                }
            }
            return null;
        }
        return new Model(this.name, this.entities);
    }

}
