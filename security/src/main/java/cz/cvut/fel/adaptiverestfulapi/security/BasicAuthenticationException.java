
package cz.cvut.fel.adaptiverestfulapi.security;


public class BasicAuthenticationException extends AuthenticationException {

    public BasicAuthenticationException() {
        this(null);
    }

    public BasicAuthenticationException(String realm) {
        super();
        // TODO Replace string with constant
        this.headers.put("WWW-Authenticate", "Basic" + (realm != null ? " realm=\"" + realm + "\"" : ""));
    }

}
