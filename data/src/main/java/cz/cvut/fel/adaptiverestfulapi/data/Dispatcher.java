
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import java.net.MalformedURLException;
import java.net.URL;


public class Dispatcher extends Filter {

    public Dispatcher() {
        this(null);
    }

    public Dispatcher(Filter next) {
        super(next);
    }

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        // TODO consider using a router
        // Router router = configuration.get("router");
        // Entity entity = router.entityName(httpContext);
        String name = this.entityName(httpContext, model);

        Entity entity = model.entityForName(name);
        HttpMethod method = httpContext.getMethod();

        if (HttpMethod.GET.equals(method)) {
            GetHandler handler = configuration.get(GetHandler.Key, entity);
            httpContext = handler.get(entity, httpContext, configuration);

        } else if (HttpMethod.POST.equals(method)) {
            PostHandler handler = configuration.get(PostHandler.Key, entity);
            httpContext = handler.post(entity, httpContext, configuration);

        } else if (HttpMethod.PUT.equals(method)) {
            PutHandler handler = configuration.get(PutHandler.Key, entity);
            httpContext = handler.put(entity, httpContext, configuration);

        } else if (HttpMethod.DELETE.equals(method)) {
            DeleteHandler handler = configuration.get(DeleteHandler.Key, entity);
            httpContext = handler.delete(entity, httpContext, configuration);

        } else {
            throw new DataException("Can not dispatch method '" + method + "'.");
        }

        return this.resign(httpContext, model, configuration);
    }

    /**
     * Extracts entity name from request URI.
     *
     * Examples
     *
     * Model: 'com.app.model'
     * URI: http://app.com/Project
     * -> com.app.model.Project
     *
     * Model: 'com.app.model'
     * URI: http://app.com/secured/Project
     * -> com.app.model.secured.Project
     *
     * @param httpContext
     * @return The name of entity.
     */
    protected String entityName(HttpContext httpContext, Model model) throws DataException {
        try {
            URL url = new URL(httpContext.getUri());
            String path = url.getPath();

            // TODO allow to have namespace, eq.: http://app.com/api/v2/package/Entity

            path = path.replaceAll("/", ".");
            return model.getName() + path;

        } catch (MalformedURLException e) {
            throw new DataException("Malformed URL: " + e.getLocalizedMessage());
        }
    }

}
