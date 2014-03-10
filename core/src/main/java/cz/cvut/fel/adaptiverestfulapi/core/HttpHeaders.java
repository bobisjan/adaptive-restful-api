
package cz.cvut.fel.adaptiverestfulapi.core;

import java.util.*;


/**
 * Class that provides unified access to HTTP headers.
 */
public class HttpHeaders implements Iterable<String> {

    // HTTP header names (alphabetic sort)
    public static final String Accept = "Accept";
    public static final String Authorization = "Authorization";
    public static final String ContentType = "Content-Type";
    public static final String WWWAuthenticate = "WWW-Authenticate";

    private Map<String, HttpHeader> data;

    public HttpHeaders() {
        this(new LinkedList<HttpHeader>());
    }

    public HttpHeaders(List<HttpHeader> headers) {
        this.data = new HashMap<>();
        for (HttpHeader entry : headers) {
            this.data.put(this.normalizeKey(entry.getKey()), entry);
        }
    }

    /**
     * Adds string value for key.
     * String should contain value and optional priority like `;q=0.4`.
     * @see http://en.wikipedia.org/wiki/Content_negotiation
     *
     * @param key
     * @param value
     */
    public void add(String key, String value) {
        String[] parts = { value };
        if (value.contains(";q=")) {
            parts = value.split(";q=");

        } else if (value.contains("; q=")) {
            parts = value.split("; q=");
        }

        if (parts.length == 2) {
            this.add(key, new HttpHeaderValue(parts[0], Double.valueOf(parts[1])));

        } else {
            this.add(key, new HttpHeaderValue(parts[0]));
        }
    }

    /**
     * Adds value for key.
     * @param key
     * @param value
     */
    public void add(String key, HttpHeaderValue value) {
        if (!this.data.containsKey(this.normalizeKey(key))) {
            List<HttpHeaderValue> values = new LinkedList<>();
            values.add(value);
            HttpHeader header = HttpHeader.create(key, values);
            this.data.put(this.normalizeKey(key), header);

        } else {
            this.data.get(this.normalizeKey(key)).add(value);
        }
    }

    /**
     * Check whether headers contains valiue for specified key.
     * @param key
     * @param value
     * @return
     */
    public boolean contains(String key, String value) {
        if (!this.data.containsKey(this.normalizeKey(key))) {
            return false;
        }
        return this.data.get(this.normalizeKey(key)).contains(value);
    }

    /**
     * Returns the value with the highest priority.
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        if (!this.data.containsKey(this.normalizeKey(key))) {
            return null;
        }
        return this.data.get(this.normalizeKey(key)).get();
    }

    public List<String> getStringValues(String key) {
        List<String> values = new LinkedList<>();
        if (!this.data.containsKey(this.normalizeKey(key))) {
            return values;
        }
        for (HttpHeaderValue value : this.data.get(this.normalizeKey(key)).getValues()) {
            values.add(value.getValue());
        }
        return values;
    }

    /**
     * Returns comma-separated string of values for specified key.
     * @param key
     * @return
     */
    public String getString(String key) {
        if (!this.data.containsKey(this.normalizeKey(key))) {
            return null;
        }
        return this.data.get(this.normalizeKey(key)).getString();
    }

    @Override
    public Iterator<String> iterator() {
        return this.data.keySet().iterator();
    }

    private String normalizeKey(String key) {
        return key.toLowerCase();
    }

}
