
package cz.cvut.fel.adaptiverestfulapi.meta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Reflection;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * For given package name creates a meta model.
 */
public class Inspector {

    private ModelInspection modeler;
    private ConfigurationInspection configurator;

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

        Set<Entity> entities = new HashSet<>();

        // phase 1: model all leaf classes
        for (Class<?> k : clazzes) {
            Entity entity = this.modeler.inspectEntity(k);
            if (entity != null) {
                if (this.isValid(entity)) {
                    entities.add(entity);

                } else {
                    this.errors.add("Entity for class " + k.getName() + " is not valid.");
                }
            }
        }

        // phase 2: model attributes and relationships
        for (Entity entity : entities) {
            Set<Triplet<Field, Method, Method>> triplets = Reflection.triplets(entity.getEntityClass());
            for (Triplet<Field, Method, Method> triplet : triplets) {
                Class type = Reflection.typeOf(triplet);
                if (Attribute.class.equals(type)) {
                    Attribute attr = this.modeler.inspectAttribute(triplet.a, triplet.b, triplet.c);
                    if (this.isValid(attr)) {
                        entity.addAttribute(attr);

                    } else {
                        this.errors.add("Attribute for " + triplet.toString() + " in entity " + entity.getName() + " is not valid");
                    }

                } else if (Relationship.class.equals(type)) {
                    Relationship rel = this.modeler.inspectRelationship(triplet.a, triplet.b, triplet.c);
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

        if (!errors.isEmpty()) {
            for (String error : this.errors) {
                this.logger.error(error);
            }
            return null;
        }

        Model model = new Model();

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
        // TODO implement configuration inspection
        return null;
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

    public void setModeler(ModelInspection modeler) {
        this.modeler = modeler;
    }

    public void setConfigurator(ConfigurationInspection configurator) {
        this.configurator = configurator;
    }

}
