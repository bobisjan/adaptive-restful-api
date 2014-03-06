
package cz.cvut.fel.adaptiverestfulapi.core;


public class FilterException extends Exception {

    protected int code;

    public FilterException() {
        this("Something gets broken during the filter process.");
    }

    public FilterException(String message) {
        this(message, 500);
    }

    public FilterException(String message, int code) {
        super(message);
        this.code = code;
    }

    public FilterException(Exception e) {
        super(e);
        this.code = 500;
    }

    public int getCode() {
        return this.code;
    }

}
