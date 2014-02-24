
package cz.cvut.fel.adaptiverestfulapi.meta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Pack;
import cz.cvut.fel.adaptiverestfulapi.meta.model.*;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Reflection;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * For given package name creates a meta model.
 */
public class Inspector {

    private ModelInspectionListener modeler;
    private ConfigurationInspectionListener configurator;

    private Logger logger = LoggerFactory.getLogger(Inspector.class);
    private List<String> errors = new LinkedList<>();

    /**
     * Inspects given package for classes that extends Object class.
     * @param pack package to model
     * @return model
     */
    public Model model(String pack) throws InspectionException {
        return this.model(pack, java.lang.Object.class);
    }

    /**
     * Inspects given package for classes that extends passed class.
     * @param pack package to model
     * @param clazz base class
     * @return model
     */
    public Model model(String pack, Class clazz) throws InspectionException {
        if (pack == null || clazz == null) {
            throw new InspectionException("Package name, or base class are missing.");
        }

        Reflections reflections = Reflection.reflections(pack, clazz);
        Set<Class<?>> clazzes = Reflection.leafs(reflections, clazz);

        if (clazzes.size() == 0) {
            this.logger.warn("There are no classes in the package \"" + pack + "\"");
            return null;
        }

        // phase 1: model all leaf classes
        Set<Entity> entities = this.entities(clazzes);

        // phase 2: model attributes and relationships
        for (Entity entity : entities) {
            this.addProperties(entity, entities);
        }

        if (!errors.isEmpty()) {
            for (String error : this.errors) {
                this.logger.error(error);
            }
            return null;
        }

        Model model = new Model(pack);

        for (Entity entity : entities) {
            model.addEntity(entity);
        }
        return model;
    }

    /**
     * Inspects given model for it's configuration.
     * @param model
     * @return configuration
     * @throws InspectionException
     */
    public Configuration configuration(Model model) throws InspectionException {
        // inspect global configuration
        Pack pack = new Pack(this.configurator.configuration());

        // inspect model configuration
        pack.addConfiguration(this.configurator.configuration(model), model.getName());

        // TODO if configuration per submodel will be needed then the implementation goes here

        // inspect entity configuration
        for (Entity entity : model.getEntities().values()) {
            pack.addConfiguration(this.configurator.configuration(entity), entity.getName(), model.getName());

            // inspect attribute configuration
            for (Attribute attribute : entity.getAttributes().values()) {
                pack.addConfiguration(this.configurator.configuration(attribute), attribute.getName() , entity.getName());
            }

            // inspect relationship configuration
            for (Relationship relationship : entity.getRelationships().values()) {
                pack.addConfiguration(this.configurator.configuration(relationship), relationship.getName() , entity.getName());
            }
        }

        return pack;
    }

    /**
     * Inspects set of classes.
     * @param clazzes
     * @return entities
     */
    protected Set<Entity> entities(Set<Class<?>> clazzes) {
        Set<Entity> entities = new HashSet<>();

        for (Class<?> clazz : clazzes) {
            Entity entity = this.modeler.entity(clazz);
            if (entity != null) {
                if (this.isValid(entity)) {
                    entities.add(entity);

                } else {
                    this.errors.add("Entity for class " + clazz.getName() + " is not valid.");
                }
            }
        }
        return entities;
    }

    /**
     * Adds properties into the entity.
     * @param entity to inspect
     * @param entities known entities
     */
    protected void addProperties(Entity entity, Set<Entity> entities) {
        Set<Triplet<Field, Method, Method>> triplets = Reflection.triplets(entity.getEntityClass());

        for (Triplet<Field, Method, Method> triplet : triplets) {
            Class type = Reflection.typeOf(triplet, entities);
            if (Attribute.class.equals(type)) {
                Attribute attr = this.modeler.attribute(triplet.a, triplet.b, triplet.c);
                if (this.isValid(attr)) {
                    entity.addAttribute(attr);

                } else {
                    this.errors.add("Attribute for " + triplet.toString() + " in entity " + entity.getName() + " is not valid");
                }

            } else if (Relationship.class.equals(type)) {
                Relationship rel = this.modeler.relationship(triplet.a, triplet.b, triplet.c);
                if (this.isValid(rel)) {
                    entity.addRelationship(rel);

                } else {
                    this.errors.add("Relationship for " + triplet.toString() + " in entity " + entity.getName() + " is not valid");
                }

            } else {
                this.errors.add("Could not resolve type of property " + triplet + " in entity " + entity.getName());
            }
        }
    }

    protected boolean isValid(Attribute attribute) {
        // TODO check for duplicates?
        if (attribute.getName() != null) {
            return true;
        }
        return false;
    }

    protected boolean isValid(Relationship relationship) {
        // TODO check for duplicates?
        if (relationship.getName() != null) {
            return true;
        }
        return false;
    }

    protected boolean isValid(Entity entity) {
        // TODO check for duplicates?
        if (entity.getName() != null && entity.getEntityClass() != null) {
            return true;
        }
        return false;
    }

    public void setModeler(ModelInspectionListener modeler) {
        this.modeler = modeler;
    }

    public void setConfigurator(ConfigurationInspectionListener configurator) {
        this.configurator = configurator;
    }

}
