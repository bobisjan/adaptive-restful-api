
package cz.cvut.fel.adaptiverestfulapi.core;

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

}
