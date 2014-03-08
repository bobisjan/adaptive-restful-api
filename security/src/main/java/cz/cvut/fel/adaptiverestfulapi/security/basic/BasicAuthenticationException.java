
package cz.cvut.fel.adaptiverestfulapi.security.basic;

import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaders;
import cz.cvut.fel.adaptiverestfulapi.security.AuthenticationException;


public class BasicAuthenticationException extends AuthenticationException {

    public BasicAuthenticationException() {
        this(null);
    }

    public BasicAuthenticationException(String realm) {
        super();
        this.headers.add(HttpHeaders.WWWAuthenticate, "Basic" + (realm != null ? " realm=\"" + realm + "\"" : ""));
    }

}
