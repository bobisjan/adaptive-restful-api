
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.InspectorListener;


public class Listener implements InspectorListener {

    @Override
    public Entity inspect(Class clazz) {
        if (clazz.getSimpleName().equalsIgnoreCase("Person")) {
            return new Entity(clazz.getSimpleName(), clazz);
        }
        return null;
    }
}
