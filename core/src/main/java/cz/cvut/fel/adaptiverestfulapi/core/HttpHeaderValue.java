
package cz.cvut.fel.adaptiverestfulapi.core;


/**
 * HTTP header value with content negotiation.
 */
public class HttpHeaderValue implements Comparable<HttpHeaderValue> {

    private final String value;
    private final Double q;

    /**
     * Creates value with no priority (maximum).
     * @param value
     */
    public HttpHeaderValue(String value) {
        this(value, Double.MAX_VALUE);
    }

    /**
     * Creates value with priority (from 0.0 to 1.0).
     * @param value
     * @param q
     */
    public HttpHeaderValue(String value, Double q) {
        if (q != Double.MAX_VALUE) {
            if (q > 1.0) {
                q = 1.0;

            } else if (q < 0.0) {
                q = 0.0;
            }
        }

        this.value = value;
        this.q = q;
    }

    @Override
    public int compareTo(HttpHeaderValue o) {
        return this.q.compareTo(o.q);
    }

    /**
     * Returns the value. Tries to cast string to the desired type.
     * Supports: String.
     *
     * @param <T> The type of the value.
     * @return The typed value.
     */
    public <T> T get() {
        Class type = null;
        try {
            type = (Class)this.getClass().getMethod("get").getReturnType();

        } catch (NoSuchMethodException e) {
            type = String.class;
        }

        // TODO add support for primitive types and datetime

        if (type.equals(String.class)) {
            return (T)this.value;

        } else {
            return (T)this.value;
        }
    }

    /**
     * Returns the value.
     * @return
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns the priority of the value.
     * @return
     */
    public Double getQ() {
        return this.q;
    }

    @Override
    public String toString() {
        String string = this.value;

        if (0.0 < this.q && this.q <= 1.0) {
            string = string + "; q=" + this.q;
        }
        return string;
    }

}
