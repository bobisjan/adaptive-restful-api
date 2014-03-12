
package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;


public interface Authorization {

    public static final String Key = "cz.cvut.fel.adaptiverestfulapi.serialization.Authorization";

    public boolean isAllowed(HttpContext httpContext);

}
