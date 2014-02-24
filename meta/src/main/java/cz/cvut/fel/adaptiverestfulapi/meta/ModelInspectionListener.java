
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public interface ModelInspectionListener {

    public Entity entity(Class clazz);

    public Attribute attribute(Field field, Method getter, Method setter);

    public Relationship relationship(Field field, Method getter, Method setter);

}
