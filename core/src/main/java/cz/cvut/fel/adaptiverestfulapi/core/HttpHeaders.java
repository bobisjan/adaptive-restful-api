
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
            this.data.put(this.normalizeKey(entry.getKey()), entry.getValue());
        }
    }

    public void add(String key, String value) {
        if (!this.data.containsKey(this.normalizeKey(key))) {
            List<String> values = new LinkedList<>();
            this.data.put(this.normalizeKey(key), values);
        }
        this.data.get(this.normalizeKey(key)).add(value);
    }

    public void put(String key, List<String> value) {
        this.data.put(key.toLowerCase(), value);
    }

    public boolean contains(String value, String key) {
        if (!this.data.containsKey(this.normalizeKey(key))) {
            return false;
        }

        for (String v : this.data.get(this.normalizeKey(key))) {
            if (v.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public <T> T get(String key) {
        if (!this.data.containsKey(this.normalizeKey(key))) {
            return null;
        }

        Class type = null;
        try {
            type = (Class)this.getClass().getMethod("get", String.class).getReturnType();

        } catch (NoSuchMethodException e) {
            type = java.util.List.class;
        }

        if (type.equals(java.util.List.class)) {
            return (T)this.data.get(this.normalizeKey(key));
        }
        return (T)this.data.get(this.normalizeKey(key)).get(0);
    }

    public String getString(String key) {
        StringBuilder sb = new StringBuilder();

        if (!this.data.containsKey(this.normalizeKey(key))) {
            return null;
        }

        List<String> values = this.data.get(this.normalizeKey(key));
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

    private String normalizeKey(String key) {
        return key.toLowerCase();
    }

}
