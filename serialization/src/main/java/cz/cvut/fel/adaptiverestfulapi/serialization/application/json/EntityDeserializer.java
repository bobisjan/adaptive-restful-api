
package cz.cvut.fel.adaptiverestfulapi.serialization.application.json;

import com.google.gson.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Attribute;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Relationship;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;


public class EntityDeserializer implements JsonDeserializer {

    private Entity entity;
    private Model model;
    private Configuration configuration;

    public EntityDeserializer(Entity entity, Model model, Configuration configuration) {
        this.entity = entity;
        this.model = model;
        this.configuration = configuration;
    }

    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        Object object = this.createInstance();

        if (!jsonElement.isJsonObject()) {
            throw new JsonParseException("JSON element for " + this.entity.getName() + " is not a JSON object");
        }

        this.deserializeAttributes(object, (JsonObject)jsonElement, type, context);
        this.deserializeRelationships(object, (JsonObject)jsonElement, type, context);

        return object;
    }

    protected Object createInstance() throws JsonParseException {
        return this.createInstance(this.entity);
    }

    protected Object createInstance(Entity entity) throws JsonParseException {
        try {
            return entity.getEntityClass().newInstance();

        } catch (InstantiationException e) {
            throw new JsonParseException(e);

        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);
        }
    }

    protected void deserializeAttributes(Object object, JsonObject jsonObject, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        for (Attribute attribute : this.entity.getAttributes().values()) {
            if (attribute.getSetter() != null && jsonObject.has(attribute.getShortName())) {
                JsonElement element = jsonObject.get(attribute.getShortName());
                this.deserializeAttribute(attribute, object, element, type, jsonDeserializationContext);
            }
        }
    }

    protected void deserializeAttribute(Attribute attribute, Object object, JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        Method setter = attribute.getSetter();

        try {
            Object value = context.deserialize(jsonElement, attribute.getAttributeType());
            setter.invoke(object, value);

        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);

        } catch (InvocationTargetException e) {
            throw new JsonParseException(e);
        }
    }

    protected void deserializeRelationships(Object object, JsonObject jsonObject, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonElement element = null;

        for (Relationship relationship : this.entity.getRelationships().values()) {
            if (relationship.getSetter() != null && jsonObject.has(relationship.getShortName())) {
                element = jsonObject.get(relationship.getShortName());

                if (relationship.isToOne()) {
                    if (element != null) {
                        this.deserializeToOne(relationship, object, element, type, context);
                    }

                } else if (relationship.isToMany()) {
                    if (element == null) {
                        element = new JsonArray();
                    }
                    if (element.isJsonArray()) {
                        this.deserializeToMany(relationship, object, (JsonArray)element, type, context);
                    }
                }
            }
        }
    }

    protected void deserializeToOne(Relationship relationship, Object object, JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        Method setter = relationship.getSetter();

        try {
            Entity targetEntity = this.model.entityForName(relationship.getTargetEntity());
            Object targetObject = this.createInstance(targetEntity);

            Attribute primary = targetEntity.getPrimary();

            Object identifier = context.deserialize(jsonElement, primary.getAttributeType());
            primary.getSetter().invoke(targetObject, identifier);

            setter.invoke(object, targetObject);

        } catch (IllegalAccessException e) {
            throw new JsonParseException(e);

        } catch (InvocationTargetException e) {
            throw new JsonParseException(e);
        }
    }

    protected void deserializeToMany(Relationship relationship, Object object, JsonArray jsonArray, Type type, JsonDeserializationContext context) throws JsonParseException {
        // TODO think, think, think
    }

}
