
package cz.cvut.fel.adaptiverestfulapi.serialization.text.plain;


import com.google.gson.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;
import cz.cvut.fel.adaptiverestfulapi.serialization.SerializationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

public class EntitySerializer {

    private Entity entity;
    private Model model;
    private Configuration configuration;

    public EntitySerializer(Entity entity, Model model, Configuration configuration) {
        this.entity = entity;
        this.model = model;
        this.configuration = configuration;
    }

    public String serialize(Object object, Type typeOfSrc) throws SerializationException {
        StringBuilder sb = new StringBuilder();

        sb.append("Entity ");
        sb.append(this.entity.getName());
        sb.append(":\n\n");

        sb.append("- attributes:\n\n");

        this.serializeAttributes(object, typeOfSrc, sb);

        sb.append("\n- relationships:\n\n");

        this.serializeRelationships(object, typeOfSrc, sb);

        return sb.toString();
    }

    protected void serializeAttributes(Object object, Type typeOfSrc, StringBuilder stringBuilder) throws SerializationException {
        for (Attribute attribute : this.entity.getAttributes().values()) {
            if (attribute.getGetter() != null) {
                this.serializeAttribute(attribute, object, typeOfSrc, stringBuilder);
            }
        }
    }

    protected void serializeAttribute(Attribute attribute, Object object, Type typeOfSrc, StringBuilder stringBuilder) throws SerializationException {
        Method getter = attribute.getGetter();
        String name = attribute.getShortName();

        try {
            Object value = getter.invoke(object);
            stringBuilder.append(name);
            stringBuilder.append(": ");
            stringBuilder.append(value);
            stringBuilder.append("\n");

        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);

        } catch (InvocationTargetException e) {
            throw new JsonParseException(e);
        }
    }

    protected void serializeRelationships(Object object, Type typeOfSrc, StringBuilder stringBuilder) throws SerializationException {
        for (Relationship relationship : this.entity.getRelationships().values()) {
            if (relationship.getGetter() != null) {
                if (relationship.isToOne()) {
                    this.serializeToOne(relationship, object, typeOfSrc, stringBuilder);

                } else if (relationship.isToMany()) {
                    this.serializeToMany(relationship, object, typeOfSrc, stringBuilder);
                }
            }
        }
    }

    /**
     * Serializes `toOne` relationship with identifier, eq: "project": 3.
     * @param relationship
     * @param object
     * @param typeOfSrc
     * @param stringBuilder
     * @throws JsonParseException
     */
    protected void serializeToOne(Relationship relationship, Object object, Type typeOfSrc, StringBuilder stringBuilder) throws SerializationException {
        Method getter = relationship.getGetter();
        String name = relationship.getShortName();

        try {
            Object target = getter.invoke(object);

            stringBuilder.append(name);
            stringBuilder.append(": ");

            if (target != null) {
                getter = this.model.entityForName(relationship.getTargetEntity()).getPrimary().getGetter();
                Object id = getter.invoke(target);

                stringBuilder.append(id);

            } else {
                stringBuilder.append("null");
            }

            stringBuilder.append("\n");

        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);

        } catch (InvocationTargetException e) {
            throw new JsonParseException(e);
        }
    }

    /**
     * Serializes `toMany` relationship as an array of IDs, eq: "issues": [1, 2, 3].
     * @param relationship
     * @param object
     * @param typeOfSrc
     * @param stringBuilder
     * @throws JsonParseException
     */
    protected void serializeToMany(Relationship relationship, Object object, Type typeOfSrc, StringBuilder stringBuilder) throws SerializationException {
        Method getter = relationship.getGetter();
        String name = relationship.getShortName();

        try {
            Collection collection = (Collection)getter.invoke(object);
            getter = this.model.entityForName(relationship.getTargetEntity()).getPrimary().getGetter();

            stringBuilder.append(name);
            stringBuilder.append(": [");

            Iterator<Object> it = collection.iterator();

            while (it.hasNext()) {
                Object id = getter.invoke(it.next());
                stringBuilder.append(id);
                if (it.hasNext()) {
                    stringBuilder.append(", ");
                }
            }

            stringBuilder.append("]");
            stringBuilder.append("\n");

        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);

        } catch (InvocationTargetException e) {
            throw new JsonParseException(e);
        }
    }

}
