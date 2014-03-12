
package cz.cvut.fel.adaptiverestfulapi.serialization.application.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.cvut.fel.adaptiverestfulapi.core.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.serialization.SerializationException;
import cz.cvut.fel.adaptiverestfulapi.serialization.Serializer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Default JSON serializer.
 */
public class JsonSerializer implements Serializer {

    public static final String MIME = "application/json;charset=UTF-8";

    private Gson gson;

    private Map<String, EntitySerializer> serializers;
    private Map<String, EntityDeserializer> deserializers;

    @Override
    public HttpContext serialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException {
        this.registerModel(model, configuration);

        Entity entity = this.entity(httpContext, model);
        Object object = httpContext.getContent();

        String json = "";
        if (object != null) {
            this.serializers.get(entity.getName()).setHttpContext(httpContext);
            json = this.gson.toJson(object);
            httpContext.response(HttpStatus.S_200, this.responseHeaders(), json);
        }
        return httpContext;
    }

    @Override
    public HttpContext deserialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException {
        this.registerModel(model, configuration);

        Entity entity = this.entity(httpContext, model);
        String json = httpContext.getRequestContent();

        if (json != null || !json.isEmpty()) {
            Object object = this.gson.fromJson(json, entity.getEntityClass());
            httpContext.setContent(object);
        }
        return httpContext;
    }

    private Entity entity(HttpContext httpContext, Model model) throws SerializationException {
        String name = this.entityName(httpContext, model);
        Entity entity = model.entityForName(name);
        if (entity == null) {
            throw new SerializationException();
        }
        return entity;
    }

    private String entityName(HttpContext httpContext, Model model) {
        return model.getName() + "." + httpContext.getRouter().getResource();
    }

    private void registerModel(Model model, Configuration configuration) {
        if (this.gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder = builder.serializeNulls();

            this.serializers = new HashMap<>();
            this.deserializers = new HashMap<>();

            for (Entity entity : model.getEntities().values()) {
                this.serializers.put(entity.getName(), new EntitySerializer(entity, model, configuration));
                this.deserializers.put(entity.getName(), new EntityDeserializer(entity, model, configuration));

                builder.registerTypeAdapter(entity.getEntityClass(), this.serializers.get(entity.getName()));
                builder.registerTypeAdapter(entity.getEntityClass(), this.deserializers.get(entity.getName()));
            }
            this.gson = builder.create();
        }
    }

    private HttpHeaders responseHeaders() {
        List<HttpHeader> headers = new LinkedList<>();
        List<HttpHeaderValue> values = new LinkedList<>();
        values.add(new HttpHeaderValue(MIME));
        headers.add(HttpHeader.create(HttpHeaders.ContentType, values));
        return new HttpHeaders(headers);
    }

}
