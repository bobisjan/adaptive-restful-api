
package cz.cvut.fel.adaptiverestfulapi.core;

import java.util.HashMap;


/**
 * Class that provides unified access to HTTP headers
 */
public class HttpHeaders extends HashMap<String, String> {

    // HTTP header names (alphabetic sort)
    public static final String Authorization = "authorization";
    public static final String WWWAuthenticate = "WWW-Authenticate";

}
