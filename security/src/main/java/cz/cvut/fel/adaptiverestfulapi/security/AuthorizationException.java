
package cz.cvut.fel.adaptiverestfulapi.security;

import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.core.HttpStatus;


public class AuthorizationException extends FilterException {

    public AuthorizationException() {
        super(HttpStatus.S_403);
    }

    public AuthorizationException(Exception e) {
        super(e);
    }

}
