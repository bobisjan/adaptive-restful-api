
package cz.cvut.fel.adaptiverestfulapi.security;

import cz.cvut.fel.adaptiverestfulapi.core.FilterException;
import cz.cvut.fel.adaptiverestfulapi.core.HttpStatus;


public class AuthenticationException extends FilterException {

    public AuthenticationException() {
        super(HttpStatus.S_401);
    }

}
