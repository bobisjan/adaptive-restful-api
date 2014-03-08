
package cz.cvut.fel.adaptiverestfulapi.core;

import java.net.MalformedURLException;
import java.net.URL;


public class HttpRouter {

    private String host;
    private int port;

    private String resource;

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
    }

    protected String resource(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
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

}
