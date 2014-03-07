
package cz.cvut.fel.adaptiverestfulapi.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Class that provides unified access to HTTP headers
 */
public class HttpHeaders implements Iterable<String> {

    // HTTP header names (alphabetic sort)
    public static final String Authorization = "Authorization";
    public static final String WWWAuthenticate = "WWW-Authenticate";

    private Map<String, String> data;

    public HttpHeaders() {
        this(new HashMap<String, String>());
    }

    public HttpHeaders(Map<String, String> data) {
        this.data = new HashMap<>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            this.data.put(entry.getKey().toLowerCase(), entry.getValue());
        }
    }

    public void put(String key, String value) {
        this.data.put(key.toLowerCase(), value);
    }

    public String get(String key) {
        return this.data.get(key.toLowerCase());
    }

    @Override
    public Iterator<String> iterator() {
        return this.data.keySet().iterator();
    }

}
