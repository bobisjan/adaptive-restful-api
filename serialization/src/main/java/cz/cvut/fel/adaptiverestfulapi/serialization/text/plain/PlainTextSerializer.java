
package cz.cvut.fel.adaptiverestfulapi.serialization.text.plain;

import cz.cvut.fel.adaptiverestfulapi.core.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.serialization.SerializationException;
import cz.cvut.fel.adaptiverestfulapi.serialization.Serializer;

import java.util.*;


public class PlainTextSerializer implements Serializer {

    public static final String MIME = "text/plain;charset=UTF-8";

    private Map<String, EntitySerializer> adapters;

    @Override
    public HttpContext serialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException {
        this.registerModel(model, configuration);

        Entity entity = this.entity(httpContext, model);
        Object object = httpContext.getContent();

        if (object != null) {
            String content = "";
            if (object instanceof Collection || object instanceof List) {
                StringBuilder sb = new StringBuilder();

                sb.append("Collection of ");
                sb.append(entity.getName());
                sb.append("\n\n\n\n");

                Collection items = (Collection)object;
                for (Object item : items) {
                    sb.append(this.adapters.get(entity.getName()).serialize(item, entity.getEntityClass()));
                    sb.append("\n\n");
                }
                content = sb.toString();

            } else {
                content = this.adapters.get(entity.getName()).serialize(object, entity.getEntityClass());
            }
            httpContext.response(HttpStatus.S_200, this.responseHeaders(), content);
        }
        return httpContext;
    }

    @Override
    public HttpContext deserialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException {
        this.registerModel(model, configuration);

        Entity entity = this.entity(httpContext, model);
        String content = httpContext.getRequestContent();

        if (content == null || content.isEmpty()) {
            return httpContext;
        }
        throw new SerializationException(HttpStatus.S_406);
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

    private HttpHeaders responseHeaders() {
        List<HttpHeader> headers = new LinkedList<>();
        List<HttpHeaderValue> values = new LinkedList<>();
        values.add(new HttpHeaderValue(MIME));
        headers.add(HttpHeader.create(HttpHeaders.ContentType, values));
        return new HttpHeaders(headers);
    }

    private void registerModel(Model model, Configuration configuration) {
        if (this.adapters == null) {
            this.adapters = new HashMap<>();

            for (Entity entity : model.getEntities().values()) {
                this.adapters.put(entity.getName(), new EntitySerializer(entity, model, configuration));
            }
        }
    }

}
