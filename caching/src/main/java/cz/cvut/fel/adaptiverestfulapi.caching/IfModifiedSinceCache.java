
package cz.cvut.fel.adaptiverestfulapi.caching;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaders;
import cz.cvut.fel.adaptiverestfulapi.core.HttpMethod;
import cz.cvut.fel.adaptiverestfulapi.core.HttpStatus;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * If-Modified-Since cache.
 *
 * Works on Entity/Identifier requests.
 *
 * It stores the timestamp on the first GET request. On another looks up for that timestamp
 * and compares it with HTTP header.
 *
 * On POST, PUT and DELETE requests updates the timestamp or removes it.
 */
public class IfModifiedSinceCache extends Cache {

    private SimpleDateFormat dateFormat;
    private Map<String, Date> timestamps;

    public IfModifiedSinceCache() {
        this.dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        this.timestamps = new HashMap<>();
    }

    @Override
    protected boolean load(HttpContext httpContext, Model model, Configuration configuration) {
        if (!HttpMethod.GET.equals(httpContext.getMethod())) {
            return false;
        }

        // Hit only resources with identifiers
        if (httpContext.getRouter().getIdentifier() == null
                || httpContext.getRouter().getIdentifier().isEmpty()) {
            return false;
        }

        // Get If-Modified-Since header
        Date ifModifiedSince = this.date(httpContext);
        if (ifModifiedSince == null) {
            return false;
        }

        String key = this.key(httpContext);
        if (!this.timestamps.containsKey(key)) {
            return false;
        }

        // Compare dates
        Date lastModified = this.timestamps.get(key);
        if (ifModifiedSince.after(lastModified)) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.LastModified, this.dateFormat.format(lastModified));
            httpContext.response(HttpStatus.S_304, httpHeaders, null);
            return true;
        }
        return false;
    }

    @Override
    protected void save(HttpContext httpContext, Model model, Configuration configuration) {
        if (httpContext.getRouter().getIdentifier() == null
                || httpContext.getRouter().getIdentifier().isEmpty()) {
            return;
        }

        if (HttpMethod.GET.equals(httpContext.getMethod())) {
            if (!this.timestamps.containsKey(this.key(httpContext))) {
                Date lastModified = new Date();
                this.timestamps.put(this.key(httpContext), lastModified);
                httpContext.getResponseHeaders().add(HttpHeaders.LastModified, this.dateFormat.format(lastModified));
            }
        } else if (HttpMethod.DELETE.equals(httpContext.getMethod())) {
            if (this.timestamps.containsKey(this.key(httpContext))) {
                this.timestamps.remove(this.key(httpContext));
            }

        } else if (HttpMethod.POST.equals(httpContext.getMethod())
                || HttpMethod.PUT.equals(httpContext.getMethod())) {
            Date lastModified = new Date();
            this.timestamps.put(this.key(httpContext), lastModified);
            httpContext.getResponseHeaders().add(HttpHeaders.LastModified, this.dateFormat.format(lastModified));
        }
    }

    private String key(HttpContext httpContext) {
        return httpContext.getRouter().getResource() + "/" + httpContext.getRouter().getIdentifier();
    }

    private Date date(HttpContext httpContext) {
        String value = httpContext.getRequestHeaders().get(HttpHeaders.IfModifiedSince);

        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return this.dateFormat.parse(value);

        } catch (ParseException e) {
            return null;
        }
    }

}
