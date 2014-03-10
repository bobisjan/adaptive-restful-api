
package cz.cvut.fel.adaptiverestfulapi.serialization.json;

import com.google.gson.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;


public class EntityAdapter implements com.google.gson.JsonSerializer, JsonDeserializer {

    private Entity entity;
    private Model model;
    private Configuration configuration;

    public EntityAdapter(Entity entity, Model model, Configuration configuration) {
        this.entity = entity;
        this.model = model;
        this.configuration = configuration;
    }

    @Override
    public JsonElement serialize(Object object, Type typeOfSrc, JsonSerializationContext context) throws JsonParseException {
        JsonObject jsonObject = new JsonObject();

        this.serializeAttributes(object, typeOfSrc, jsonObject, context);
        this.serializeRelationships(object, typeOfSrc, jsonObject, context);

        return jsonObject;
    }

    protected void serializeAttributes(Object object, Type typeOfSrc, JsonObject jsonObject, JsonSerializationContext context) throws JsonParseException {
        Method getter = null;

        for (Attribute attr : this.entity.getAttributes().values()) {
            getter = attr.getGetter();

            if (getter != null) {
                this.serializeAttribute(attr, object, typeOfSrc, jsonObject, context);
            }
        }
    }

    protected void serializeAttribute(Attribute attribute, Object object, Type typeOfSrc, JsonObject jsonObject, JsonSerializationContext context) throws JsonParseException {
        Method getter = attribute.getGetter();
        String name = attribute.getShortName();

        try {
            Object value = getter.invoke(object);
            jsonObject.add(name, context.serialize(value));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new JsonParseException(e);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new JsonParseException(e);
        }
    }

    protected void serializeRelationships(Object object, Type typeOfSrc, JsonObject jsonObject, JsonSerializationContext context) throws JsonParseException {
        Method getter = null;

        for (Relationship rel : this.entity.getRelationships().values()) {
            getter = rel.getGetter();

            if (getter == null) {
                continue;
            }

            if (rel.isToOne()) {
                this.serializeToOne(rel, object, typeOfSrc, jsonObject, context);

            } else if (rel.isToMany()) {
                this.serializeToMany(rel, object, typeOfSrc, jsonObject, context);
            }
        }
    }

    protected void serializeToOne(Relationship relationship, Object object, Type typeOfSrc, JsonObject jsonObject, JsonSerializationContext context) throws JsonParseException {
        Method getter = relationship.getGetter();
        String name = relationship.getShortName();

        try {
            Object target = getter.invoke(object);
            getter = this.model.entityForName(relationship.getTargetEntity()).getPrimary().getGetter();

            Object id = getter.invoke(target);
            jsonObject.add(name, context.serialize(id));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new JsonParseException(e);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new JsonParseException(e);
        }
    }

    protected void serializeToMany(Relationship relationship, Object object, Type typeOfSrc, JsonObject jsonObject, JsonSerializationContext context) throws JsonParseException {
        Method getter = relationship.getGetter();
        String name = relationship.getShortName();

        try {
            Collection collection = (Collection)getter.invoke(object);
            getter = this.model.entityForName(relationship.getTargetEntity()).getPrimary().getGetter();

            JsonArray ids = new JsonArray();

            for (Object obj : collection) {
                Object id = getter.invoke(obj);
            }
            jsonObject.add(name, ids);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new JsonParseException(e);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new JsonParseException(e);
        }
    }

    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

}
