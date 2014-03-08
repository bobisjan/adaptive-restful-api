
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.FilterException;


public class DataException extends FilterException {

    public DataException() {
        this("Something gets broken during the data process.");
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(Exception e) {
        super(e);
    }

}
