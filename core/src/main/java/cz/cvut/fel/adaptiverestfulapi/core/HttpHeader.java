
package cz.cvut.fel.adaptiverestfulapi.core;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Represents HTTP header.
 */
public class HttpHeader {

    private String key;
    private List<HttpHeaderValue> values;

    public static HttpHeader create(String key, List<HttpHeaderValue> values) {
        if (values.size() > 0) {
            return new HttpHeader(key, values);
        }
        return null;
    }

    private HttpHeader(String key, List<HttpHeaderValue> values) {
        this.key = key;
        List<HttpHeaderValue> copy = new LinkedList<>();
        for (HttpHeaderValue value : values) {
            copy.add(value);
        }
        Collections.sort(copy);
        Collections.reverse(copy);
        this.values = copy;
    }

    /**
     * Returns key.
     * @return
     */
    public String getKey() {
        return this.key;
    }

    public void add(HttpHeaderValue value) {
        this.values.add(value);
        Collections.sort(this.values);
        Collections.reverse(this.values);
    }

    /**
     * Returns the value with the highest priority.
     * @param <T>
     * @return
     */
    public <T> T get() {
        return this.values.get(0).get();
    }

    /**
     * Returns list of values.
     * @return
     */
    public List<HttpHeaderValue> getValues() {
        return Collections.unmodifiableList(this.values);
    }

    /**
     * Returns comma-separated string of values.
     * @return
     */
    public String getString() {
        StringBuilder sb = new StringBuilder();
        Iterator<HttpHeaderValue> iterator = this.values.iterator();

        while (iterator.hasNext()){
            sb.append(iterator.next().getValue());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * Check whether header contains specified value.
     * @param value
     * @return
     */
    public boolean contains(String value) {
        return this.contains(new HttpHeaderValue(value));
    }

    /**
     * Checks whether header contains specified value.
     * @param value
     * @return
     */
    public boolean contains(HttpHeaderValue value) {
        for (HttpHeaderValue val : this.values) {
            if (val.getValue().equalsIgnoreCase(value.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.key);
        sb.append(": ");
        sb.append(this.getString());

        return sb.toString();
    }

}
