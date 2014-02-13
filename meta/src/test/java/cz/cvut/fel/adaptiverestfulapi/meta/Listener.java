
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.InspectorListener;


public class Listener implements InspectorListener {

    @Override
    public Entity inspect(Class klass) {
        if (klass.getSimpleName().equalsIgnoreCase("Person")) {
            return new Entity(klass.getSimpleName(), klass);
        }
        return null;
    }
}
