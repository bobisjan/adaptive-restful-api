
package cz.cvut.fel.adaptiverestfulapi.core;


public class FilterException extends Exception {

    public FilterException() {
        this("Something gets broken during the filter process.");
    }

    public FilterException(String message) {
        super(message);
    }

}
