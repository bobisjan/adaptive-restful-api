
package cz.cvut.fel.adaptiverestfulapi.core;

import java.util.*;


/**
 * Class that provides unified access to HTTP headers
 */
public class HttpHeaders implements Iterable<String> {

    // HTTP header names (alphabetic sort)
    public static final String Authorization = "Authorization";
    public static final String WWWAuthenticate = "WWW-Authenticate";

    private Map<String, List<String>> data;

    public HttpHeaders() {
        this(new HashMap<String, List<String>>());
    }

    public HttpHeaders(Map<String, List<String>> data) {
        this.data = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            this.data.put(entry.getKey().toLowerCase(), entry.getValue());
        }
    }

    public void add(String key, String value) {
        if (!this.data.containsKey(key)) {
            List<String> values = new LinkedList<>();
            this.data.put(key, values);
        }
        this.data.get(key).add(value);
    }

    public void put(String key, List<String> value) {
        this.data.put(key.toLowerCase(), value);
    }

    public <T> T get(String key) {
        if (!this.data.containsKey(key)) {
            return null;
        }

        Class type = null;
        try {
            type = (Class)this.getClass().getMethod("get").getReturnType();

        } catch (NoSuchMethodException e) {
            type = java.util.List.class;
        }

        if (type.equals(java.util.List.class)) {
            return (T)this.data.get(key);
        }
        return (T)this.data.get(key).get(0);
    }

    public String getString(String key) {
        StringBuilder sb = new StringBuilder();

        List<String> values = this.get(key);
        Iterator<String> iterator = values.iterator();

        while (iterator.hasNext()){
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return this.data.keySet().iterator();
    }

}
