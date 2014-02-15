
package cz.cvut.fel.adaptiverestfulapi.meta;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * For given package name creates a meta model.
 */
public class Inspector {

    private InspectorListener listener;

    private Logger logger = LoggerFactory.getLogger(Inspector.class);

    /**
     * Inspects given package for classes that extends Object class.
     * @param pack package to inspect
     * @return model
     */
    public Model inspect(String pack) throws InspectionException {
        return this.inspect(pack, java.lang.Object.class);
    }

    /**
     * Inspects given package for classes that extends passed class.
     * @param pack package to inspect
     * @param clazz base class
     * @return model
     */
    public Model inspect(String pack, Class clazz) throws InspectionException {
        if (this.listener == null) {
            // TODO throw exception?
            System.err.println("There is no listener for inspector.");
            return null;
        }
        if (pack == null || clazz == null) {
            throw new InspectionException("Package name, or base class are missing.");
        }

        Reflections reflections = this.reflections(pack, clazz);
        Set<Class<?>> clazzes = reflections.getSubTypesOf(clazz);

        if (clazzes.size() == 0) {
            this.logger.warn("There are no classes in the package \"" + pack + "\"");
            return null;
        }

        Model model = new Model();

        for (Class<?> k : clazzes) {
            Entity entity = this.listener.inspectEntity(k);
            if (entity != null) {
                if (this.validate(entity)) {
                    // TODO inspect fields and methods
                    model.getEntities().put(entity.getName(), entity);

                } else {
                    // TODO throw exception?
                    System.err.println("Entity for class " + k.getName() + " is not valid.");
                }
            }
        }
        return model;
    }

    private Reflections reflections(String pack, Class clazz) {
        if (clazz.equals(Object.class)) {
            // @see http://stackoverflow.com/a/9571146
            List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
            classLoadersList.add(ClasspathHelper.contextClassLoader());
            classLoadersList.add(ClasspathHelper.staticClassLoader());

            return new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pack))));
        }
        return new Reflections(clazz);
    }

    protected boolean validate(Entity entity) {
        // TODO add entity validator?
        return true;
    }

    public void setListener(InspectorListener listener) {
        this.listener = listener;
    }

}
