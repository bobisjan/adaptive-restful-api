
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaders;
import cz.cvut.fel.adaptiverestfulapi.core.HttpStatus;


public class DataException extends FilterException {

    public DataException() {
        this("Something gets broken during the data process.");
    }

    public DataException(String message) {
        this(message, HttpStatus.S_500, new HttpHeaders());
    }

    public DataException(HttpStatus status) {
        super(status.getMessage(), status, new HttpHeaders());
    }

    public DataException(String message, HttpStatus status, HttpHeaders headers) {
        super(message, status, headers);
    }

    public DataException(Exception e) {
        super(e);
    }

}
