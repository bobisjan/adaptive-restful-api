
package cz.cvut.fel.adaptiverestfulapi.meta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public interface InspectorListener {

    public Entity inspectEntity(Class clazz);

    public Attribute inspectAttribute(Field field, Method getter, Method setter);

    public Relationship inspectRelationship(Field field, Method getter, Method setter);

}
