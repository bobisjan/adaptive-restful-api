
package cz.cvut.fel.adaptiverestfulapi.meta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.HashSet;

import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Pack;
import cz.cvut.fel.adaptiverestfulapi.meta.model.*;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Reflection;
import cz.cvut.fel.adaptiverestfulapi.meta.reflection.Triplet;
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

        ModelBuilder builder = new ModelBuilder(pack, clazz);
        Reflection reflection = new Reflection(builder.getName(), builder.getBaseClass());

        // phase 1: model all leaf classes
        this.addEntities(builder, reflection);

        // phase 2: model attributes and relationships
        this.addProperties(builder, reflection);

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
     * Inspects classes for the builder.
     * @param builder
     */
    protected void addEntities(ModelBuilder builder, Reflection reflection) {
        Set<Class<?>> clazzes = reflection.leafs(builder.getBaseClass());

        for (Class<?> clazz : clazzes) {
            Entity entity = this.modeler.entity(clazz);
            builder.addEntity(entity);
        }
    }

    /**
     * Inspects properties for entities in the builder.
     * @param builder
     */
    protected void addProperties(ModelBuilder builder, Reflection reflection) {
        for (Entity entity : builder.getEntities()) {
            Set<Triplet<Field, Method, Method>> triplets = reflection.triplets(entity.getEntityClass());

            for (Triplet<Field, Method, Method> triplet : triplets) {
                Property property = this.modeler.property(triplet.a, triplet.b, triplet.c);
                builder.addProperty(property, entity);
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
