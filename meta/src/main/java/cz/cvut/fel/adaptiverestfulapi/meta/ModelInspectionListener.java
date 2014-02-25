
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public interface ModelInspectionListener {

    public Entity entity(Class clazz);

    public Property property(Field field, Method getter, Method setter);

}
