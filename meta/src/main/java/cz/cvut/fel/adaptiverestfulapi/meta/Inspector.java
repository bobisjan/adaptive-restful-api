
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

        ModelBuilder builder = new ModelBuilder(pack);

        // phase 1: model all leaf classes
        this.addEntities(builder, clazzes);

        // phase 2: model attributes and relationships
        this.addProperties(builder);

        return builder.build(this.logger);
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
        pack.addConfiguration(this.configurator.configuration(model), model);

        // TODO if configuration per submodel will be needed then the implementation goes here

        // inspect entity configuration
        for (Entity entity : model.getEntities().values()) {
            pack.addConfiguration(this.configurator.configuration(entity), entity, model);

            // inspect attribute configuration
            for (Attribute attribute : entity.getAttributes().values()) {
                pack.addConfiguration(this.configurator.configuration(attribute), attribute, entity);
            }

            // inspect relationship configuration
            for (Relationship relationship : entity.getRelationships().values()) {
                pack.addConfiguration(this.configurator.configuration(relationship), relationship, entity);
            }
        }

        return pack;
    }

    /**
     * Inspects set of classes.
     * @param builder
     * @param clazzes
     */
    protected void addEntities(ModelBuilder builder, Set<Class<?>> clazzes) {
        for (Class<?> clazz : clazzes) {
            Entity entity = this.modeler.entity(clazz);
            builder.addEntity(entity);
        }
    }

    /**
     * Inspects properties for entities in the builder.
     * @param builder
     */
    protected void addProperties(ModelBuilder builder) {
        Set<Entity> entities = new HashSet<>(builder.getEntities().values());

        for (Entity entity : entities) {
            Set<Triplet<Field, Method, Method>> triplets = Reflection.triplets(entity.getEntityClass());

            for (Triplet<Field, Method, Method> triplet : triplets) {
                Class type = Reflection.typeOf(triplet, entities);
                if (Attribute.class.equals(type)) {
                    Attribute attribute = this.modeler.attribute(triplet.a, triplet.b, triplet.c);
                    builder.addAttribute(attribute, entity);

                } else if (Relationship.class.equals(type)) {
                    Relationship relationship = this.modeler.relationship(triplet.a, triplet.b, triplet.c);
                    builder.addRelationship(relationship, entity);

                } else {
                    builder.addError("Could not resolve type of property " + triplet + " in entity " + entity.getName());
                }
            }
        }
    }

    public void setModeler(ModelInspectionListener modeler) {
        this.modeler = modeler;
    }

    public void setConfigurator(ConfigurationInspectionListener configurator) {
        this.configurator = configurator;
    }

}
