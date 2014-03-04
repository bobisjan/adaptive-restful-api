
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Entity;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import java.net.MalformedURLException;
import java.net.URL;


public class Dispatcher extends Filter {

    public static final String Handler = "cz.cvut.fel.adaptiverestfulapi.data.Handler";

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
        Handler handler = configuration.get(Dispatcher.Handler, entity);

        this.dispatch(entity, handler, httpContext, configuration);
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

    /**
     * Dispatches method to the data handler and stores the result into the context.
     * @param entity
     * @param handler
     * @param httpContext
     * @param configuration
     * @throws DataException
     */
    protected void dispatch(Entity entity, Handler handler, HttpContext httpContext, Configuration configuration) throws DataException {
        HttpMethod method = httpContext.getMethod();
        Object result = null;

        if (HttpMethod.GET.equals(method)) {
            result = handler.GET(entity, httpContext, configuration);

        } else if (HttpMethod.POST.equals(method)) {
            result = handler.POST(entity, httpContext, configuration);

        } else if (HttpMethod.PUT.equals(method)) {
            result = handler.PUT(entity, httpContext, configuration);

        } else if (HttpMethod.DELETE.equals(method)) {
            result = handler.DELETE(entity, httpContext, configuration);

        } else {
            throw new DataException("Can not dispatch method '" + method + "'.");
        }

        httpContext.setContent(result);
        httpContext.response(HttpStatus.S_200, new HttpHeaders(), result.toString());
    }

}
