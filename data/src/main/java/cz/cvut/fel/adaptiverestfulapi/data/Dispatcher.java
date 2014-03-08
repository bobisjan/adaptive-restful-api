
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Data dispatcher.
 * Dispatches data processing to the concrete handler based on the HTTP method and entity.
 */
public class Dispatcher extends Filter {

    public Dispatcher() {
        this(null);
    }

    public Dispatcher(Filter next) {
        super(next);
    }

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        String name = this.entityName(httpContext, model);

        Entity entity = model.entityForName(name);
        HttpMethod method = httpContext.getMethod();

        Handler handler = this.handler(method, entity, configuration);
        httpContext = handler.handle(entity, httpContext, configuration);

        return this.resign(httpContext, model, configuration);
    }

    /**
     * Looks up for data handler for HTTP method and entity in the configuration.
     * @param method The HTTP method.
     * @param entity The entity.
     * @param configuration The configuration.
     * @return The data handler.
     * @throws DataException
     */
    protected Handler handler(HttpMethod method, Entity entity, Configuration configuration) throws DataException {
        if (HttpMethod.GET.equals(method)) {
            return configuration.get(GetHandler.Key, entity);

        } else if (HttpMethod.POST.equals(method)) {
            return configuration.get(PostHandler.Key, entity);

        } else if (HttpMethod.PUT.equals(method)) {
            return configuration.get(PutHandler.Key, entity);

        } else if (HttpMethod.DELETE.equals(method)) {
            return configuration.get(DeleteHandler.Key, entity);

        } else {
            throw new DataException("Can not dispatch method '" + method + "'.");
        }
    }

    private String entityName(HttpContext httpContext, Model model) {
        return model.getName() + "." + httpContext.getRouter().getResource();
    }

}
