
package cz.cvut.fel.adaptiverestfulapi.core;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpRouter {

    private String host;
    private int port;

    private String resource;
    private String identifier;

    public static HttpRouter createRouter(String url) {
        try {
            return new HttpRouter(url);

        } catch (MalformedURLException e) {
            return null;
        }
    }

    private HttpRouter(String url) throws MalformedURLException {
        URL URL = new URL(url);

        this.host = URL.getHost();
        this.port = URL.getPort();
        this.resource = this.resource(URL.getPath());
        this.identifier = this.identifier(URL.getPath());
    }

    protected String resource(String path) {
        String[] parts = this.pathParts(path);
        if (parts.length > 0) {
            return parts[0];
        }
        return null;
    }

    protected String identifier(String path) {
        String[] parts = this.pathParts(path);
        if (parts.length == 2) {
            return parts[1];
        }
        return null;
    }

    private String[] pathParts(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path.split("/");
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getResource() {
        return this.resource;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Object getIdentifier(Type type) {
        if (this.identifier == null || this.identifier.isEmpty()) {
            return null;
        }

        // TODO add support for primitive types

        if (type.equals(Integer.class)) {
            return Integer.valueOf(this.identifier);

        } else if (type.equals(Long.class)) {
            return Long.valueOf(this.identifier);

        } else {
            // String class or default
            return this.identifier;
        }
    }

}
