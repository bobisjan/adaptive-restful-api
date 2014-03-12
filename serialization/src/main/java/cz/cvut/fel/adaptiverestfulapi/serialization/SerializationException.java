
package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaders;
import cz.cvut.fel.adaptiverestfulapi.core.HttpStatus;


public class SerializationException extends FilterException {

    public SerializationException() {
        this(HttpStatus.S_500);
    }

    public SerializationException(HttpStatus status) {
        super(status.getMessage(), status, new HttpHeaders());
    }

}
